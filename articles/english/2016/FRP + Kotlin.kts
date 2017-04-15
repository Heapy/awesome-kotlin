
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """

![Photo from meetup](http://i.imgur.com/DOlI1Of.jpg)

[Slides](https://drive.google.com/file/d/0BxCm4NRlzb3PWjNNaG1KS0Utckk/view)

"""

Article(
  title = "FRP + Kotlin",
  url = "https://drive.google.com/file/d/0BxCm4NRlzb3PWjNNaG1KS0Utckk/view",
  categories = listOf(
    "Kotlin",
    "FP"
  ),
  type = slides,
  lang = EN,
  author = "Giorgio Natili",
  date = LocalDate.of(2016, 11, 25),
  body = body
)
