:page/title UTC now!
:blog-post/tags [:tech :programming :dotnet :csharp]
:blog-post/author {:person/id :einarwh}
:page/body

# UTC now!

Posted: May 26, 2011 

The other day, I stumbled across this post, describing how .NET developers are alledgedly misuing DateTime.Now. The gist of it is that we are using DateTime.Now in cases where DateTime.UtcNow would be more appropriate, for instance when measuring runtime performance. The difference between Now and UtcNow is that the former gives you local time, whereas the latter gives you “universal” or location-independent time. It turns out that Now is one or two orders of magnitude slower than UtcNow; the reason being that Now has to derive the local time by calculating an offset from universal time depending on locale. Clearly, this is unnecessary work if all you’re going to do is calculate the time elapsed between two points in time. Based on this, the author of the blog post argues that using Now when you could have used UtcNow is wrong.

I’m of two minds about this issue. My gut reaction was that, “gee, that’s really unimportant”. Or to borrow a phrase from one of my co-workers, “I would look for performance optimisations elsewhere”. So Now is much slower than UtcNow. I didn’t know that, but then again I don’t think it’s a big deal. Performance doesn’t matter until it does – that is, when something is perceived as unacceptably slow by a user. Of course, the post includes the mandatory contrived example, showing how the difference between Now and UtcNow matters when current time is sampled a million times in a tight loop. Yup. EVERYTHING matters when you’re doing it a million times. (Especially if you do it on some thread where there’s a user waiting in the other end.) Nevertheless, I find it entirely plausible that in a practical scenario, there would be other things you needed to do a million times besides getting the time, which just might dwarf the demonstrated performance difference. Put it another way: I estimate there’s something like a one-in-a-million chance that you’re actually going to be in a real world situation where this is going to be a problem. Now for that one guy in Duluth, Minnesota who actually has to get the current time – and nothing else – a million consecutively for some business critical routine, it’s going to matter if he knows about the performance difference between Now and UtcNow. I hope he does. For the rest of us, not so much.

On the flip side though: now that I know, I’ll never again use Now if I can use UtcNow instead. Isn’t that ironic? I’ve just claimed that this has no practical implications whatsoever, and yet: In the future, whenever I’m doing some relative measurement of time, it’s UtcNow all the way. Unless it’s StopWatch, which is usually much more convenient (and uses UtcNow under the hood by default). After all, there’s no reason to waste clock cycles on purpose. Heh.

Lurking underneath this rather trivial matter, though, is a somewhat deeper issue. Which is: should I have known?

Isn’t it negligence on my part that I was unaware of the performance implications of using Now instead of UtcNow? After all, I’m a professional developer, right? I’ve heard Uncle Bob‘s sermons about craftmanship and I’m a believer. My employer pays me good money each month to produce the best code I’m able to. In turn, my employer’s customers rely on the quality of that code. And I’ve put my share of Now‘s in there, you know? Without needing to. Superflous work. Burnt cycles. UtcNow would have been just fine. Not that it matters, performance-wise, in any way, shape or form – if and when I’ve written sluggish code, it’s not because of Now – it’s because I’ve chosen a suboptimal algorithm or data structure at some key point in the application. Still: the right thing would have been to use UtcNow unless I needed local time, dammit! And I’ve plunged ahead, unwittingly using Now instead, all due to my incomplete knowledge of the .NET framework. Isn’t that sloppy?

As you can imagine, I’m totally going to exonerate myself on this issue. No, I should not have known! I mean, I could have known, it would have been nice if I would have known, but I don’t think I should have known. You see, programmers work in a constant state of partial, incomplete understanding. This is not an anomaly, it’s the name of the game. Modern software is just too large and complex to make it feasible to grok it all unless you’re L. Peter Deutsch or something. And you probably aren’t. This certainly goes for the vast .NET framework. According to this post, version 3.5 of the .NET framework contained 11417 types and 109657 members. Complete understanding is not only unattainable, it’s also economically unsound to aim for. Since program understanding is a slow and costly process, your efforts should be focussed on understanding what is needed to solve the problem at hand. You need just enough program understanding to implement a feature or fix a bug or whatever you need to do. Just-in-time program understanding is a lean and pragmatic approach that allows you to get things done. (Note that I’m not advocating programming by coincidence here. Clearly you have to understand, at some level, what the code you’re invoking does. But you do so at the appropriate level of abstraction. You shouldn’t have to fire up Reflector every time you’re using an API function.)

If anything, it’s an API design issue. (Blaming it on Microsoft is always a safe bet!) When using an API, you try to map the problem you need solved onto the members of the API. You scan the available properties and methods, and conjecture a hypothesis regarding which one will fulfill your need. In doing so, you reach for the simplest-sounding potential match first. Only when that doesn’t work for you do you consider the next most plausible candidate. You can think of this as API affordance – the API will invite you, through it’s vocabulary, to try out certain things first. If you need Foo to happen and the API supports methods Foo() and FrobnitzFoo(), you’re going to try Foo() first. Right? Only when the plain Foo() fails to Foo as you’d like it to will you try FrobnitzFoo() instead. I’m sure you can see the parallel to Now/UtcNow. Since Microsoft gave the straight-forward simplest name to the property that gives you local time, that’s what developers reach for, even when they just need a relative timestamp. It’s pure economics. And lo and behold! it works, because for all but the poor schmuck in Duluth, Now gets the job done just fine. The hypothesis is never falsified. (Of course, that guy from Duluth is going to end up an enlightened schmuck, because for him, the hypothesis will be falsified – he’s going to get burned by the relatively slow implementation, probe around the DateTime API, maybe even crank out Reflector, and eventually find that “hey, I should be using UtcNow instead”. And he’ll be good to go, and all smug about it to boot.)

Of course, we can speculate as to why Microsoft used the name ‘Now‘ instead of something like ‘LocalNow‘, which would have been more precise. Is it accidental? Due to negligence or incompetence? Probably not. Now is broadly applicable and good enough in all but the most extreme scenarios. It can be used successfully both for showing local time to the user (which is usually the right thing to do), and for measuring runtime performance (even though it’s not the optimal choice in the latter case). I think they did it very deliberately. And I don’t think it’s a bad thing. If Microsoft made a mistake, it’s that Now is a property. In general, the rule is that properties should be instantenous. According to the MSDN documentation, a method is preferable to a property when “[it] performs a time-consuming operation, [that is] perceivably slower than the time it takes to set or get a field’s value”. Arguably, then, Now should really have been Now(). That would have provided a subtle hint that getting the current time isn’t free. And these long posts about DateTime.Now wouldn’t have been written at all.