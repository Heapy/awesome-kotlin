---
title: 'Kotlin без магии'
url: http://javanese.online/%D1%81%D1%82%D0%B0%D1%82%D1%8C%D0%B8/Kotlin_%D0%B1%D0%B5%D0%B7_%D0%BC%D0%B0%D0%B3%D0%B8%D0%B8/
categories:
    - Kotlin
author: Mike Gorunov
date: Dec 28, 2016 04:00
---
Обычно, когда открываешь для себя новый язык программирования, некоторые его конструкции выглядят, как магия. Например, for-each loop в Java:

```kotlin
for (String s : strings) {
    /* ... */
}
```

Какого типа должна быть ```strings```? Во что разворачивается эта конструкция?

(для массива (допустим, ```T[]```) получается подобие ```for (int i = 0; i < array.length; i++) { T t = array[i]; /* ... */ }```, а для ```Iterable<T>``` — нечто вроде ```for (Iterator<T> itr = iterable.iterator; itr.hasNext(); ) { T t = itr.next(); /* ... */ }```)

Или, скажем, как работает try-with-resources ( ```try (InputStream is = new FileInputStream(file)) { /* ... */ }```)? Какой ```finally``` вызовется раньше — сгенерированный компилятором и закрывающий ресурсы или наш собственный? Что нужно сделать чтобы собственный класс работал с try-with-resources? <small>(ну да, реализовать ```AutoCloseable```)</small>

Пример ещё проще и повседневнее.

```java
String first = getStringSomewhere();
String second = "first: " + first;
String third = "first: " + first + "; hashCode: " + first.hashCode();
```

Как переопределён оператор сложения? Где будет создаваться ```StringBuilder```, а где — нет?

Ещё пример. Код на Groovy.

```groovy
def personDetails = [firstName:'John', lastName:'Doe', age:25]
```

Какой класс у personDetails? ```HashMap```, ```TreeMap```, ```LinkedHashMap```, ```Collections$UnmodifiableMap```?

## Что там про Kotlin?

В Kotlin нет магических и тайных языковых конструкций.

```Array<T>.forEach и Collection<T>.forEach``` — это extension functions, которые определены как ```for (element in this) action(element)```. При Ctrl-клике (Cmd-клике) на ```in``` можно увидеть, что этот оператор работает с функциями ```iterator```, ```next``` и ```hasNext```. Последние, в свою очередь, определены как ```operator fun```, что и позволяет использовать их с языковыми конструкциями. То есть автор класса, написав ```operator fun```, явно разрешает использовать эту функцию как конструкцию языка.

В выражении ```someFile.bufferedReader.use { /* тот же try-with-resources */ }```, например, ```use``` — инлайновая (встраиваемая) extension-функция для ```Closeable```. Можно посмотреть в её код и понять, когда там вызывается ```finally```.

В коде ```val s = "a" + "b"``` можно Ctrl-кликнуть плюс и увидеть ```operator fun plus``` в классе ```kotlin.String```.

В конструкции ```val personDetails = mapOf("firstName" to "John", "lastName" to "Doe", "age" to 25)``` можно заглянуть в реализацию ```mapOf``` (также есть ```mutableMapOf```, ```hashMapOf```, ```linkedMapOf```) и в реализацию ```to``` (```infix fun <a, b="">A.to(that: B): Pair <a, b="">= Pair(this, that)</a,></a,>```).

Лично для меня при всём синтаксическом разнообразии и гибкости Kotlin код на нём остаётся очень понятным. Можно создавать свои функции, которые благодаря модификаторам ```operator```, ```inline``` и ```infix``` выглядят как языковые конструкции, но всегда можно посмотреть в их реализацию, и нигде не будет чего-то вроде ```*тут магия компилятора*```.
