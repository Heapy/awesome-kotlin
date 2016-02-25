/*
Sample feed item:

{
    title: '',
    description: `Content, can be HTML.`,
    url: '',
    categories: ['Category 1', 'Category 2', 'Category 3', 'Category 4'],
    author: 'Yoda Master',
    date: 'Feb 24, 2016 11:00',
    enclosure: {
        url: 'http://site.com/file.jpg',
        size: 1337,
        type: 'image/jpeg'
    }
}
*/

exports.default = [{
    title: '[RU] Kotlin 1.0. Задай вопрос команде.',
    description: `На этой неделе случилось важное для нас событие — вышла первая версия языка программирования Kotlin!  Так как почти вся разработка Kotlin велась в Питерском офисе компании JetBrains, многие хабровчане уже знают, что такое Kotlin и пробовали его на практике, поэтому этот пост больше для комментариев: задавайте любые вопросы и команда Kotlin ответит. Мы онлайн!`,
    url: 'https://habrahabr.ru/company/JetBrains/blog/277573/',
    categories: ['JetBrains', 'Kotlin'],
    author: 'Роман Белов',
    date: 'Feb 19, 2016 16:30'
}, {
    title: 'A Very Peculiar, but Possibly Cunning Kotlin Language Feature.',
    description: `This has caught me by surprise. After studying the Kotlin language to learn about how to best leverage this interesting new language for jOOQ, I stumbled upon this puzzler. What do you think the following program will print? fun main(args: Array) { (1..5).forEach { if (it == 3) return print(it) } print("done") } Well... You...`,
    url: 'http://blog.jooq.org/2016/02/22/a-very-peculiar-but-possibly-cunning-kotlin-language-feature/',
    categories: ['Kotlin', 'Puzzlers'],
    author: 'Lukas Eder',
    date: 'Feb 22, 2016 15:33'
}, {
    title: '[RU] Podcast Разбор Полетов: Episode 102 — Kotlin, тесты и здоровый сон.',
    description: 'Kotlin 1.0 RC - Философия Kotlin.',
    url: 'http://razbor-poletov.com/2016/02/episode-102.html',
    categories: ['Kotlin', 'Podcast'],
    author: 'Dmitry Jemerov, Viktor Gamov, Alexey Abashev, Anton Arphipov,  Dmitry Churbanov, Anton Arhipov',
    date: 'Feb 04, 2016 21:54',
    enclosure: {
        url: 'http://traffic.libsyn.com/razborpoletov/razbor_102.mp3',
        size: 70319173
    }
}, {
    title: 'Kotlin 1.0 Released: Pragmatic Language for JVM and Android',
    description: `This is it. 1.0 is here! It’s been a long and exciting road but we’ve finally reached the first big 1.0, and we’re celebrating the release by also presenting you with the new logo.`,
    url: 'http://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/',
    categories: ['JetBrains', 'Kotlin'],
    author: 'Andrey Breslav',
    date: 'Feb 15, 2016 12:57',
    enclosure: {
        url: 'http://i0.wp.com/blog.jetbrains.com/kotlin/files/2016/02/1_0_Banner.png',
        size: 22038
    }
}, {
    title: 'JVM Newcomer Kotlin 1.0 is GA',
    description: `After a long and winding road to development, pragmatic JVM and Android newcomer Kotlin 1.0 is officially GA. The open source progeny of Java IDE supremo JetBrains (it was developed on GitHub under the Apache 2.0 Open-Source license), Kotlin has been cooking since 2010, and as you’d expect, runs seamlessly on the company’s signature Java IDE, IntelliJ IDEA.`,
    url: 'https://www.voxxed.com/blog/2016/02/kotlin/',
    categories: ['Kotlin', 'JVM'],
    author: 'Lucy Carey',
    date: 'Feb 15, 2016 17:50'
}, {
    title: '[RU] Релиз Kotlin 1.0, языка программирования для JVM и Android.',
    description: `Компания Jetbrains, развивающая интегрированную среду разработки IntelliJ IDEA, представила первый значительный релиз объектно-ориентированного языка программирования Kotlin 1.0, позволяющего создавать приложения, скомпилированные для последующего выполнения внутри стандартной виртуальной машины Java (JVM) или Android. Дополнительно поддерживается преобразование программ в JavaScript-представление для запуска внутри браузера, но данная функциональность пока отнесена к экспериментальным возможностям.`,
    url: 'http://www.opennet.ru/opennews/art.shtml?num=43882',
    categories: ['Kotlin', 'JVM  '],
    author: 'Open Source',
    date: 'Feb 16, 2016 11:21'
}, {
    title: 'Kotlin 1.0: The good, the bad and the evident.',
    description: `After a long wait, Kotlin 1.0 is finally here! According to the official announcement, ”Kotlin works everywhere where Java works: server-side applications, mobile applications (Android), desktop applications. It works with all major tools and services such as IntelliJ IDEA, Android Studio and Eclipse, Maven, Gradle and Ant, Spring Boot, GitHub, Slack and even Minecraft.”`,
    url: 'https://jaxenter.com/kotlin-1-0-the-good-the-bad-and-the-evidence-124041.html',
    categories: ['Kotlin', 'Review'],
    author: 'Gabriela Motroc',
    date: 'Feb 16, 2016 17:11'
}, {
    title: 'Developing Spring Boot applications with Kotlin.',
    description: `Just in time for Kotlin 1.0 release, we are adding support for Kotlin language to https://start.spring.io in order to make it easier to start new Spring Boot projects with this language. \n This blog post is also an opportunity for me to explain why I find this language interesting, to show you a sample project in detail and to give you some hints.`,
    url: 'https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin',
    categories: ['Kotlin', 'Spring'],
    author: 'Sébastien Deleuze',
    date: 'Feb 15, 2016 12:58'
}, {
    title: '[RU] Немного о Kotlin.',
    description: `На днях JetBrains после пятилетней работы выпустила первый релиз языка Kotlin. Давайте посмотрим, что же это за язык, попробуем разобраться зачем и для кого он, какие имеет функциональные особенности. Скорее всего в статью затесались и личные впечатления от языка, но я старался, чтобы они не влияли на изложение полезной информации. Если вы еще ничего или почти ничего не знаете о Kotlin, то я завидую вам, ибо по моему ощущению почитать про инструмент, который ты долго ждал, сродни распаковке новогоднего подарка. Впрочем судите сами.`,
    url: 'https://habrahabr.ru/post/277479/',
    categories: ['Kotlin', 'Review'],
    author: '@fogone',
    date: 'Feb 20, 2016  08:25'
}, {
    title: '[RU] DevZen Podcast: Kotlin и Vulkan 1.0 — Episode 0080.',
    description: `Темы выпуска: Сравнение разных конфигурации сети в Kubernetes, Kotlin наконец вышел в версии 1.0, Vulkan тоже вышел в 1.0, критическая уязвимость в glibc, ZFS в Ubuntu 16.04, снова Rust, и про Монады. И, конечно, ответы на вопросы слушателей.`,
    url: 'http://devzen.ru/episode-0080/',
    categories: ['Podcast', 'Kotlin'],
    author: 'DevZen Podcast',
    date: 'Feb 20, 2016 11:34',
    enclosure: {
        url: 'http://devzen.ru/download/2016/devzen-0080-2016-02-19-3280e712a2cc1485.mp3',
        size: 55240704
    }
}, {
    title: 'The Kobalt diaries: testing',
    description: `<p><a href="http://beust.com/kobalt">Kobalt</a> automatically detects how to run your tests based on the test dependencies that you declared:</p><div class="syntaxhighlighter"><div class="lines"><div class="line alt1"><table><tbody><tr><td class="content"><code class="plain">dependenciesTest {</code></td></tr></tbody></table></div><div class="line alt2"><table><tbody><tr><td class="content"><code class="spaces">&nbsp;&nbsp;&nbsp;&nbsp;</code><code class="plain">compile(</code><code class="string">"org.testng:testng:6.9.9"</code><code class="plain">)</code></td></tr></tbody></table></div><div class="line alt1"><table><tbody><tr><td class="content"><code class="plain">}</code></td></tr></tbody></table></div></div></div><p>By default, Kobalt supports TestNG, JUnit and Spek. You can also configure how your tests run<br>with the <code>test{}</code> directive:</p>`,
    url: 'http://beust.com/weblog/2016/02/20/the-kobalt-diaries-testing/',
    categories: ['Kotlin', 'Kobalt'],
    author: 'Cédric Beust',
    date: 'Feb 20, 2016 10:35'
}, {
    title: '[RU] Радио-Т 484',
    description: `Kotlin дорос до версии 1.0`,
    url: 'https://radio-t.com/p/2016/02/20/podcast-484/',
    categories: ['Podcast', 'Kotlin'],
    author: 'Umputun, Bobuk, Gray, Ksenks',
    date: 'Feb 20, 2016 15:44',
    enclosure: {
        url: 'http://cdn.radio-t.com/rt_podcast484.mp3',
        size: 72259834
    }
}, {
    title: 'Kotlin — Love at first line',
    description: require('./articles/1').default,
    url: 'https://medium.com/@dime.kotevski/kotlin-love-at-first-line-7127befe240f#.p5hp6dxlh',
    categories: ['Anko', 'Android', 'Kotlin'],
    author: 'Dimitar Kotevski',
    date: 'Feb 21, 2016 12:13'
}];
