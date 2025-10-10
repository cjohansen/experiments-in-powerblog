:page/title Coin-flip architecture
:blog-post/tags [:tech ]
:blog-post/author {:person/id :einarwh}

<!-- :blog-post/published #time/ldt "2014-12-27T00:00:00" -->

:page/body

# Coin-flip architecture

<p class="blog-post-date">February 8, 2025</p>

I have been writing and talking about socio-technical API patterns. Such patterns are a subset of a larger group of socio-technical integration patterns.

I particularly interesting socio-technical integration pattern is _the stalemate_, in which two or more autonomous teams can't reach an agreement on how to integrate their systems. Classic: synchronous vs asynchronous integration. Each solution has pros and cons.

The basic socio-technical situation looks like this. The awkwardness is palpable.

Autonomy brings about these kinds of situations.

What should you do if the teams fail to reach an agreement?

Either the integration is unimportant to the enterprise, or an agreement must be reached.

If you have two autonomous teams that can’t reach an agreement on something, threaten them with an architect.

«Hvis dere ikke blir enige kommer arkitekten!»

The purpose is not to actually bring in an architect to break up the stalemate, but to make the teams realize that there are worse options and outcomes than whatever the other team is proposing.

Ref team som ikke klarer å bli enige om integrasjonsmønstre så er jo en løsning ikke å gjøre noe verdens ting for dem, men spørre dem hva de har levert den siste uka? Den siste måneden? Det siste halvåret? Hvis de er kryssfunksjonelle autonome team med leveranseansvar så må det vel bety at de tenker å ha et svar på slike spørsmål etter hvert? Evt slå kron og mynt. Tilfeldighet er ærlig og bedre enn å ta side.

Dvs på sikt må det vel være fordi det er viktig for noen?

Så klart. Hvis teamene ikke klarer å ta ansvar for bli enige, så får man slå kron og mynt for dem da. Begge deler vil jo funke.

Og så føler jeg jo at casen deres for autonomi har fått et skudd for baugen da. Å ta ansvar er også å ta ansvar for å bli enige. Selv om det betyr at man tar «feil» avgjørelse noen ganger. Den dårligste avgjørelsen er ingen avgjørelse. Det er det eneste alternativet som i hvert fall helt garantert ikke virker.

If you’re an architect brought in to resolve a dispute between two autonomous teams who would rather never deliver than reach a compromise about some architectural decision, flip a coin. A coin is neutral, and both teams’ solutions are bound to have both merits and drawbacks.

By flipping a coin you will out-perform the typical architect who will come up with a third solution that both teams will hate and may not actually work.

It’s important to realize that the real problem is not the technical choice, it is the social stalemate.
