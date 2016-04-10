---
title: '(RU) Андрей Бреслав и Дмитрий Жемеров о Kotlin 1.0 на jug.msk.ru'
url: https://habrahabr.ru/post/279667/
categories:
    - Kotlin
author: Дмитрий Белобородов
date: Mar 22, 2016 00:37
---
17 марта 2016 года прошла встреча [jug.msk.ru](http://jug.msk.ru/) с Андреем Бреславом и Дмитрием Жемеровым, посвящённая выпуску версии 1.0 языка программирования Kotlin. Далее рассказывается о прошедшей встрече подробнее.

![КПВД](https://habrastorage.org/files/c29/a51/0ef/c29a510efb444bb4bc8b18f8ad740523.jpg)

О докладчиках

Андрей Бреслав ( [@abreslav](https://habrahabr.ru/users/abreslav/) ) является архитектором языка _Kotlin_, занимаясь его разработкой в компании _JetBrains_ с 2010 года.

Ниже приведён список видео докладов Андрея, которые удалось найти:

* «BrainStorm. Автоматизированная оптимизация аппаратно-программных архитектур» (_Computer Science клуб при ПОМИ РАН_, 2011: [видео](https://www.youtube.com/watch?v=wZXEhngRzMA))
* «Синтаксический анализ для встроенных языков» (_Computer Science клуб при ПОМИ РАН_, 2011: [видео](https://www.youtube.com/watch?v=PARloe1mPkc))
* «Project Kotlin» (_JUG.ru-2012_: [видео](https://www.youtube.com/watch?v=wjkaPXT_vY4&index=11&list=PLVe-2wcL84b8pj7VOoa-6L9Q0sDjibdoF))
* «Функции и данные в Kotlin» (_FProg-12 в JetBrains_: [видео](https://www.youtube.com/watch?v=0AzLhiic0fM))
* «Type-safe Web with Kotlin» (_JPoint 2013_: [видео](https://www.youtube.com/watch?v=6y-4xJWFLl4&index=10&list=PLVe-2wcL84b8pj7VOoa-6L9Q0sDjibdoF))
* «Компромиссы, или Как проектируются языки программирования» (_Joker 2013_: [видео](https://www.youtube.com/watch?v=CX_K1r0Vklg&index=8&list=PLVe-2wcL84b8pj7VOoa-6L9Q0sDjibdoF))
* «Компромиссы в разработке языков программирования» (_JPoint 2014_: [видео](https://www.youtube.com/watch?v=HE4yyPpUsy4&index=7&list=PLVe-2wcL84b8pj7VOoa-6L9Q0sDjibdoF))
* «Kotlin для Android: коротко и ясно» (_Mobius 2014_: [видео](https://www.youtube.com/watch?v=VU_L2_XGQ9s&index=9&list=PLVe-2wcL84b8pj7VOoa-6L9Q0sDjibdoF))
* «Язык Kotlin для платформы Java» (_JEEConf 2015_: [видео](https://www.youtube.com/watch?v=018n0aXiljc))
* «Kotlin: Challenges in language design» (_Curry On, Prague 2015_: [видео](https://www.youtube.com/watch?v=zVZFv80l_lQ))
* «Что делать?» (2015: [видео](https://www.youtube.com/watch?v=P_qrDQMBRzw))

Два доклада я смотрел очно на конференциях, ещё один — в записи. К моему большому удивлению, последний в списке докладов является вовсе не техническим.


Дмитрий Жемеров ( [@yole](https://habrahabr.ru/users/yole/) ) работает в _JetBrains_ с 2003 года (на некоторое время уходил в _Google_ и вернулся обратно), успев поучаствовать во многих проектах компании, в _IntelliJ IDEA_ в частности. Сейчас Дмитрий руководит командой разработки _Kotlin_ - плагина и пишет книгу [Kotlin in Action](https://www.manning.com/books/kotlin-in-action) (в соавторстве со Светланой Исаковой).

На [его сайте](http://yole.ru) перечислено ещё несколько персональных проектов (в т.ч. [Syndirella](https://sourceforge.net/projects/syndirella/), про которую [упоминал](https://radio-t.com/p/2016/02/20/podcast-484/) недавно [@bobuk](https://habrahabr.ru/users/bobuk/)).

Доклады Дмитрия, которые удалось найти:

* «Kotlin» (_Riviera DEV 2011_: [видео](https://www.youtube.com/watch?v=P0SisYQrosg))
* «Kotlin» (_CZJUG-2013_: [видео](https://www.youtube.com/watch?v=acLBPytzpEI))
* «Static types in JavaScript: what, how and why» (_JSConf EU 2013_: [видео](https://www.youtube.com/watch?v=0r9HPRJUaFo))
* «Why Python Sucks» (_Europython 2013_: [видео](https://www.youtube.com/watch?v=PlXEsrhF1iE))
* «SDK, Gradle, AndroidStudio» (_Онлайн школа Android-разработчиков-2015_: [видео](https://www.youtube.com/watch?v=ZyPgFuPwXoQ))
* «Без слайдов: интервью с Дмитрием Жемеровым из JetBrains» (_Хабрахабр_: [статья с видео](https://habrahabr.ru/company/jugru/blog/263905/))
* «Опыт использования Kotlin в JetBrains» (Joker 2015: [видео](https://www.youtube.com/watch?v=c1tf_zLGMKM&index=6&list=PLVe-2wcL84b8pj7VOoa-6L9Q0sDjibdoF))

Из перечисленного смотрел только один доклад с конференции в записи и интервью из серии «Без слайдов».

Совсем недавно Андрей и Дмитрий вместе выступали с докладом «Kotlin 1.0» на встрече _JUG.ru_ в Питере — [статья с видео](https://habrahabr.ru/company/jugru/blog/278647/) доклада.

#### О докладе

До встречи в течение нескольких вечеров почитал [описание языка](https://kotlinlang.org/docs/reference/), посмотрел [примеры](https://github.com/JetBrains/kotlin-examples), попробовал писать код. Есть [поддержка Ant](https://kotlinlang.org/docs/reference/using-ant.html) (уже анахронизм), [Maven](https://kotlinlang.org/docs/reference/using-maven.html) и [Gradle](https://kotlinlang.org/docs/reference/using-gradle.html). Пользоваться языком достаточно приятно. Интеграция с _IntelliJ IDEA_, естественно, есть и весьма хорошая.

Успел послушать три выпуска подкастов, в которых отметились докладчики, представляя версию 1.0 языка _Kotlin_:

* [Разбор полётов, выпуск 102](http://razbor-poletov.com/2016/02/episode-102.html) (Дмитрий Жемеров)
* [Радио-Т, выпуск 484](https://radio-t.com/p/2016/02/20/podcast-484/) (Дмитрий Жемеров)
* [SDCast, выпуск 41](https://sdcast.ksdaemon.ru/2016/03/sdcast-41/) (Андрей Бреслав)

Видео питерского выступления на _JUG.ru_ ранее посмотреть тоже успел. В значительной степени содержание совпадало, но были и отличия. Последняя часть (вопросов и ответов), конечно же, полностью отличалась.

![](https://habrastorage.org/files/ebc/899/747/ebc8997475c04c1c82bf59daf46f7d21.jpg)

Как и в Питере, выступление состояло из четырёх частей:

* вводная часть с рассказом о языке (Андрей Бреслав);
* планы развития после версии 1.0 (Дмитрий Жемеров);
* вопросы совместимости (Андрей Бреслав);
* сессия вопросов и ответов.

В перерыве слушатели могли пообщаться с докладчиками.

![](https://habrastorage.org/files/e0d/ffc/356/e0dffc35632a4c43aade34c9f3efa386.jpg)

Предполагаю, что наиболее интересной частью и для Андрея с Дмитрием была сессия вопросов и ответов. Надеюсь, что для докладчиков тоже был некий элемент неожиданности, т.к. интересные вопросы были. Удивило, что уже есть люди (кроме _JetBrains_), активно использующие язык в промышленной эксплуатации.

Очень хорошо, что задающим вопросы давался микрофон — было слышно, что спрашивают. Записанное видео можно будет комфортно слушать, не напрягаясь при попытке расслышать вопросы.

![](https://habrastorage.org/files/342/253/3f8/3422533f8cc548c8a83e2f03eff97409.jpg)

Спасибо Андрею Бреславу и Дмитрию Жемерову за интересный доклад, Андрею Когуню (_jug.msk.ru_, _КРОК_) и Роману Белову (_JetBrains_) — за организацию мероприятия.

Ссылки по теме доклада — языку _Kotlin_:

* [основной сайт](https://kotlinlang.org);
* [Try Online](http://try.kotlinlang.org);
* [описание языка](https://kotlinlang.org/docs/reference/);
* книги [Kotlin in Action](https://www.manning.com/books/kotlin-in-action) и [Kotlin for Android Developers](https://leanpub.com/kotlin-for-android-developers);
* [Kotlin Koans](https://kotlinlang.org/docs/tutorials/koans.html);
* [Kotlin Educational Plugin](http://blog.jetbrains.com/kotlin/2016/03/kotlin-educational-plugin/) (выпущен в день встречи, 17 марта).

Фото и видео будут доступны [здесь](http://vk.com/jugmsk) и [здесь](https://plus.google.com/communities/115981831554057619568). На рассылку о следующих встречах _jug.msk.ru_ подписаться можно [здесь](http://jug.msk.ru).
