/*
Sample feed item:

{
    title: '',
    description: 'Content, can be HTML.',
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
    description: `На этой неделе случилось важное для нас событие — вышла первая версия языка программирования Kotlin! 
    Так как почти вся разработка Kotlin велась в Питерском офисе компании JetBrains, многие хабровчане уже знают, 
    что такое Kotlin и пробовали его на практике, поэтому этот пост больше для комментариев: задавайте любые вопросы и команда Kotlin ответит. Мы онлайн!`,
    url: 'https://habrahabr.ru/company/JetBrains/blog/277573/',
    categories: ['JetBrains', 'Kotlin'],
    author: 'Роман Белов',
    date: 'Feb 19, 2016 16:30'
}, {
    title: 'A Very Peculiar, but Possibly Cunning Kotlin Language Feature.',
    description: `This has caught me by surprise. 
    After studying the Kotlin language to learn about how to best leverage this interesting new language for jOOQ, I stumbled upon this puzzler. 
    What do you think the following program will print? fun main(args: Array) { (1..5).forEach { if (it == 3) return print(it) } print("done") } Well... You...`,
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
        url: 'http://i0.wp.com/blog.jetbrains.com/kotlin/files/2016/02/1_0_Banner.png'
    }
}];
