/*
Sample feed item:

{
    title: '',
    description: 'Content, can be HTML.',
    url: '',
    categories: ['Category 1', 'Category 2', 'Category 3', 'Category 4'],
    author: 'Yoda Master',
    date: 'Feb 24, 2016',
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
    date: 'Feb 19, 2016'
}];
