
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.IT
import link.kotlin.scripts.LinkType.video
import java.time.LocalDate

// language=Markdown
val body = """
Dopo cinque anni di sviluppo JetBrains rilascia la prima versione di Kotlin, linguaggio staticamente tipizzato che pur rimanendo pienamente compatibile con Java cerca di risolverne alcuni problemi tutt'ora aperti. Presenterò un semplice programma realizzato con approccio OO/funzionale e confronteremo l'implementazione Java 8 con quella Kotlin.

Francesco Vasco: Lavoro da 16 anni in varie realtà utilizzando prevalentemente tecnologie in ambito Java e da febbraio ho iniziato ad utilizzare Kotlin sia in campo amatoriale che professionale.

[JUG Milano meeting #86](http://www.jugmilano.it/meeting-86.html)

<iframe width="960" height="480" src="https://www.youtube.com/embed/BUAxqiGrKOc" frameborder="0" allowfullscreen></iframe>
"""

Article(
  title = "Costruiamo un treno in Kotlin",
  url = "https://www.youtube.com/watch?v=y-T6DCtNUG0",
  categories = listOf(
    "Kotlin"
  ),
  type = video,
  lang = IT,
  author = "Francesco Vasco",
  date = LocalDate.of(2016, 12, 19),
  body = body
)

