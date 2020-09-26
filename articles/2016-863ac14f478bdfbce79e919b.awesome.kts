
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.RU
import link.kotlin.scripts.LinkType.video
import java.time.LocalDate

// language=Markdown
val body = """
Kotlin очень хорошо зашёл в сообщество Android разработчиков, в то же время множество разработчиков всё так же опасаются попробовать использовать в production.
В этом выпуске я пригласил Яна Жуланова, из компании Jetbrains, одного из разработчиков Kotlin, а именно специализирующемся на Kotlin для Android.
Вместе мы обсудим почему вас стоит попробовать Kotlin, если вы его ещё не пробовали, обсудим популярные мифы и опасения о Kotlin и обсудим какие новости нам ожидают c Kotlin в ближайшем будущем.

Упоминания:

* Kotlin/anko: Pleasant Android application development – http://bit.ly/2hQvGsA
* Scaloid makes your Android code easy to understand and maintain – http://bit.ly/2hDH8r5
* XTend – Modernized Java – http://bit.ly/2hPCiF5
* Используем Kotlin для написания скриптов Gradle – http://bit.ly/2ib9CFN
* Why You Must Try Kotlin For Android Development? – http://bit.ly/2hQy8PW
* Android Development with Kotlin — Jake Wharton – http://bit.ly/2h698mk
* Advancing Android Development with Kotlin – Jake Wharton – http://bit.ly/2hPANa2
* Kotlin 1.0.5 is here с улучшенной поддержкой Lint – http://bit.ly/2hPFAZj
* Kotlin vs Java: Compilations speed – http://bit.ly/2h6eDBE
* Kotlin 1.1: first glimpse – http://bit.ly/2gXP7LJ
* Дмитрий Жемеров — Ой, котик побежал: Компиляция и производительность кода на Kotlin – http://bit.ly/2hPHnx8
* Kotlin Native – http://bit.ly/2hPATOW
* Kotlin Weekly – http://bit.ly/2hPDZ5D
* Kotlin Slack – http://bit.ly/2hQww8I
* Kotlin Youtrack – http://bit.ly/2hDPj6I
* Kotlin Discussions – http://bit.ly/2h66tsU

<iframe width="960" height="480" src="https://www.youtube.com/embed/d6795d1aN3U" frameborder="0" allowfullscreen></iframe>

"""

Article(
  title = "Kotlin: Будущие изменения и текущие мифы",
  url = "https://www.youtube.com/watch?v=d6795d1aN3U",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = video,
  lang = RU,
  author = "Android в Лицах",
  date = LocalDate.of(2016, 12, 20),
  body = body
)
