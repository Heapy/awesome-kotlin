
import link.kotlin.scripts.Article
import link.kotlin.scripts.Enclosure
import link.kotlin.scripts.LanguageCodes.RU
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
* [Kotlin дорос до версии 1.0](http://thenextweb.com/dd/2016/02/15/kotlin-the-pragmatic-language-for-android-and-jvm-has-reached-its-1-0-release/) - 00:02:45.
* [В чем его прагматизм](https://dzone.com/articles/kotlin-10-is-now-available) - 00:12:57.
* Версия 3 iTerm2 - 00:42:04.
* Странная история борьбы Apple - 00:52:37.
* GitHub добавил поддержку шаблонов - 01:10:35.
* Go 1.6 - 01:15:12.
* Страшный баг угрожает нашим серверам - 01:21:21.
* Архитектура Stack Overflow на 2016 - 01:26:33.
* Custom Machine Types - конфигурации по вкусу - 01:31:15.
* Темы наших слушателей

"""

Article(
  title = "Радио-Т 484",
  url = "https://radio-t.com/p/2016/02/20/podcast-484/",
  categories = listOf(
    "Podcast",
    "Kotlin"
  ),
  type = article,
  lang = RU,
  author = "Umputun, Bobuk, Gray, Ksenks",
  date = LocalDate.of(2016, 2, 20),
  body = body,
  enclosure = Enclosure(
    url = "http://cdn.radio-t.com/rt_podcast484.mp3",
    size = 72259834
  )
)
