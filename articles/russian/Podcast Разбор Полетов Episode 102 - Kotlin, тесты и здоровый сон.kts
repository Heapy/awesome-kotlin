
import link.kotlin.scripts.Article
import link.kotlin.scripts.Enclosure
import link.kotlin.scripts.model.LanguageCodes.RU
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

## Мероприятия

1. http://jbreak.ru/
1. https://fosdem.org/2016/

## Темки

1. Kotlin 1.0 RC - Философия Kotlin
1. JavaTest, TCK, JTreg и его связь с TestNG/JUnit и прочие вопросы в знатоку по этим вопросам
1. Что нужно есть и сколько нужно спать, что бы быть белым русским мужчиной в Америке

## Полезняшки

1. Клиент для Redis - [Medis](https://github.com/luin/medis)
1. http://kotlin.link/
1. https://github.com/Originate/git-town
1. https://libraries.io/

"""

Article(
  title = "Podcast Разбор Полетов: Episode 102 — Kotlin, тесты и здоровый сон.",
  url = "http://razbor-poletov.com/2016/02/episode-102.html",
  categories = listOf(
    "Kotlin",
    "Podcast"
  ),
  type = article,
  lang = RU,
  author = "Dmitry Jemerov, Viktor Gamov, Alexey Abashev, Anton Arphipov,  Dmitry Churbanov, Anton Arhipov",
  date = LocalDate.of(2016, 2, 4),
  body = body,
  enclosure = Enclosure(
    url = "http://traffic.libsyn.com/razborpoletov/razbor_102.mp3",
    size = 70319173
  )
)
