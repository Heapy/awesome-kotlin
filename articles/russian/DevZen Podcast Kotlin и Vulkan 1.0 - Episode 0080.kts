
import link.kotlin.scripts.Article
import link.kotlin.scripts.Enclosure
import link.kotlin.scripts.LanguageCodes.RU
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
Темы выпуска: Сравнение разных конфигурации сети в Kubernetes, Kotlin наконец вышел в версии 1.0, Vulkan тоже вышел в 1.0, критическая уязвимость в glibc, ZFS в Ubuntu 16.04, снова Rust, и про Монады. И, конечно, ответы на вопросы слушателей.

Шоу нотес:

* Вакансия C++ & Scala Software Engineer
* Comparison of Networking Solutions for Kubernetes — Comparison of Networking Solutions for Kubernetes 2 documentation
* [Kotlin 1.0 Released: Pragmatic Language for JVM and Android | Kotlin Blog](http://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/)
* Go 1.6 is released — The Go Blog
* Does FreeBSD support OpenGL 3.3?
* Unable to build Assimp on FreeBSD 10.2 · Issue #795 · assimp/assimp · GitHub
* Vulkan — Industry Forged
* Vulkan in 30 minutes
* OpenNews: Опубликован графический стандарт Vulkan 1.0
* Khronos Products
* Vulkan 1.0 Released: What You Need To Know About This Cross-Platform, High-Performance Graphics API — Phoronix
* PVS-Studio покопался в ядре FreeBSD
* Критическая уязвимость библиотеки glibc позволяет осуществлять удаленное выполнение кода / Блог компании Positive Technologies / Хабрахабр
* FreeBSD and CVE-2015-7547
* OpenNews: В Ubuntu 16.04 будет добавлена поддержка ZFS и Vulkan
* From the Canyon Edge: ZFS is *the* FS for Containers in Ubuntu 16.04!
* There is No Now — ACM Queue
* select * from depesz; » Blog Archive » Waiting for 9.6 – Remove GROUP BY columns that are functionally dependent on other columns.
* Hint Bits — PostgreSQL wiki
* Speeding up things with hint bits — Cybertec — The PostgreSQL Database Company
* Introduction to Windows shellcode development – Part 3 – Security Café
* The latest high-end Ubuntu phone will be available globally | ZDNet
* CharybdeFS: a new fault-injecting filesystem for software testing
* Red Book, 5th ed. Ch. 6: Weak Isolation and Distribution
* PostgreSQL: Documentation: 9.5: Transaction Isolation
* [Libevent-users] Comparison of libevent and libuv
* Kazuho’s Weblog: The reasons I stopped using libuv for H2O
* Using non-blocking and asynchronous I/O (CK10 problem) in Linux and Windows (with epool, iocp, libevent/libev/libuv/boost.asio and librt/libaio) | Rui’s Blog
* Запах монад по утрам — Прогулки по воде
* Темы и вопросы слушателей для 0080 « DevZen Podcast

"""

Article(
  title = "DevZen Podcast: Kotlin и Vulkan 1.0 — Episode 0080.",
  url = "http://devzen.ru/episode-0080/",
  categories = listOf(
    "Podcast",
    "Kotlin"
  ),
  type = article,
  lang = RU,
  author = "DevZen Podcast",
  date = LocalDate.of(2016, 2, 20),
  body = body,
  enclosure = Enclosure(
    url = "http://devzen.ru/download/2016/devzen-0080-2016-02-19-3280e712a2cc1485.mp3",
    size = 55240704
  )
)
