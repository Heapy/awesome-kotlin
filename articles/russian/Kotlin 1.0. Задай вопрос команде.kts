
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.RU
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
## Kotlin 1.0. Задай вопрос команде

На этой неделе случилось важное для нас событие — [вышла первая версия](http://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/) языка программирования [Kotlin](http://kotlinlang.org/)! Так как почти вся разработка Kotlin велась в Питерском офисе компании JetBrains, многие хабровчане уже знают, что такое Kotlin и пробовали его на практике, поэтому этот пост больше для комментариев: задавайте любые вопросы и команда Kotlin ответит. Мы онлайн!

![Kotlin](https://habrastorage.org/getpro/habr/post_images/2f8/f4e/685/2f8f4e6857445ecef579ae6e96e80c60.png)


Для тех, кто слышит о Kotlin впервые, а так же для затравки разговора, несколько фактов о Kotlin:

* Kotlin — это “прагматичный” язык для JVM и Android, который мы в JetBrains написали, для того чтобы нам было на чем программировать ;)
*Kotlin имеет ту же облаcть применимости, что и Javа, и совместим со всеми современными технологиями и инструментами:
    * [IntelliJ IDEA](http://kotlinlang.org/docs/tutorials/getting-started.html), [Android Studio](http://kotlinlang.org/docs/tutorials/kotlin-android.html), [Eclipse](http://kotlinlang.org/docs/tutorials/getting-started-eclipse.html)
    * [Maven](http://kotlinlang.org/docs/reference/using-maven.html), [Gradle](http://kotlinlang.org/docs/reference/using-gradle.html), [Ant](http://kotlinlang.org/docs/reference/using-ant.html)
    * [Spring Boot](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin)
* Kotlin полностью [совместим с Java](http://kotlinlang.org/docs/reference/java-interop.html). В смешанных проектах код на Kotlin может легко вызывать код на Java и наоборот. Соответственно, все существующие Java-библиотеки доступны из Kotlin.
* В Kotlin нелегко получить Null Pointer Exception, потому что система типов исключает это.
* Kotlin позволяет [создавать функции-расширения](http://kotlinlang.org/docs/reference/extensions.html) для существующих классов. Поэтому стандартная библиотека не определяет свои классы коллекций, но предоставляет кучу удобных функций для работы с JDK коллекциями
* Начиная с версии 1.0 мы гарантируем бинарную совместимость. Так что теперь можно ;)
* Нас много! За январь Kotlin использовало больше 11 тысяч человек, из них 5 тысяч за последнюю неделю. Почти 2 тысячи человек общаются в нашем Slack-канале и помогают друг другу. Сейчас в команде Kotlin больше двадцати человек. В JetBrains написано около 500К строчек кода на Kotlin, более чем в десяти проектах, а в открытых репозиториях на GitHub (исключая наши) число строчек растет экспоненциально, и на момент релиза их уже больше двух миллионов: ![Kotlin GitHub](https://habrastorage.org/getpro/habr/post_images/75e/087/333/75e087333d725b9f69ec3d009abf17fe.gif)
* Мы планируем вскоре зарелизить поддержку JavaScript и Java 8
* У нас есть своя онлайн-песочница: [http://try.kotl.in](http://try.kotl.in/) В ней есть серия задачек [Kotlin Koans](http://try.kotl.in/koans), которые помогают освоиться с языком буквально за несколько часов. Действительно за несколько часов! Также у нас отличная документация на [официальном сайте](http://kotlinlang.org/docs/reference/).
* Мы открыты! Kotlin [разрабатывается на GitHub](https://github.com/JetBrains/kotlin), под лицензией Apache 2.0. Но самое главное, мы всегда внимательно прислушиваемся ко всему входящему фидбеку, так что теперь слово вам:

"""

Article(
  title = "Kotlin 1.0. Задай вопрос команде.",
  url = "https://habrahabr.ru/company/JetBrains/blog/277573/",
  categories = listOf(
    "JetBrains",
    "Kotlin"
  ),
  type = article,
  lang = RU,
  author = "Роман Белов",
  date = LocalDate.of(2016, 2, 19),
  body = body
)
