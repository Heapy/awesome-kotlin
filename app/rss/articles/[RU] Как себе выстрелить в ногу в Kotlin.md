---
title: 'Как себе выстрелить в ногу в Kotlin'
url: https://habrahabr.ru/post/278169/
categories:
    - Puzzlers
    - Kotlin
author: '@ov7a'
date: Feb 29, 2016 06:24
---
> Пост-наброс

## Как себе выстрелить в ногу в Kotlin

Совсем недавно вышел [релиз](http://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/) Kotlin, а его команда разработчиков предлагала [задавать вопросы](https://habrahabr.ru/company/JetBrains/blog/277573/) про язык. Он сейчас на слуху и, возможно, многим хочется его попробовать.

Пару недель назад тимлид сделал для компании презентацию о том, что в Котлине хорошо. Одним из самых интересных вопросов был «А как в Котлине выстрелить себе в ногу?» Так получилось, что ответил на этот вопрос я.

Disclaimer:
Не стоит воспринимать эту статью как «Kotlin — отстой». Хотя я отношусь скорее к категории тех, кому и со Scala хорошо, я считаю, что язык неплохой.
Все пункты спорные, но раз в год и палка стреляет. Когда-то вы себе прострелите заодно и башку, а когда-то у вас получится выстрелить только в полночь полнолуния, если вы предварительно совершите черный ритуал создания плохого кода.

Наша команда недавно закончила большой проект на Scala, сейчас делаем проект помельче на Kotlin, поэтому в спойлерах будет сравнение со Scala. Я буду считать, что Nullable в Kotlin — это эквивалент Option, хотя это совсем не так, но, скорее всего, большинство из тех, кто работал с Option, будут вместо него использовать Nullable.

#### 1\. Пост-инкремент и преинкремент как выражения

Цитирую вопрошавшего: «Фу, это ж баян, скучно». Столько копий сломано, миллион вопросов на собеседованиях C++... Если есть привычка, то можно было его оставить инструкцией (statement'ом). Справедливости ради, другие операторы, вроде +=, являются инструкциями.
[Цитирую](https://habrahabr.ru/company/JetBrains/blog/277573/#comment_8784811) одного из разработчиков, [abreslav](https://habrahabr.ru/users/abreslav/):

> Смотрели на юзкейсы, увидели, что поломается, решили оставить.
>
> Замечу, что у нас тут не С++, и на собеседовании про инкремент спросить особо нечего. Разве что разницу между префиксным и постфиксным.

На нет и суда нет. Разумеется, в здравом уме никто так делать не будет, но случайно — может быть.

```kotlin
var i = 5
i = i++ + i++
println(i)
```

> **Никакого undefined behaviour, результат, очевидно, 12**
>
> 11

```kotlin
var a = 5
a = ++a + ++a
println(a)
```

> **Тут все проще, конечно, 14**
>
> 13

> **Больше примеров**
>
> ```kotlin
> var b = 5
> b = ++b + b++
> println(b)
> ```
>
> > **Банальная логика говорит, что ответ должен быть между 11 и 13**
> >
> > да, 12
> >
> > ```kotlin
> > var c = 5
> > c = c++ + ++c
> > println(c)
> > ```
>
> > **От перестановки мест слагаемых сумма не меняется**
> >
> > разумеется, 12
>
> ```kotlin
> var d = 5
> d = d + d++ + ++d + ++d
> println(d)
>
> var e = 5
> e = ++e + ++e + e++ + e
> println(e)
> ```
>
> > **От перестановки мест слагаемых сумма не меняется!**
> >
> > Разумеется:
> > 25
> > 28

> **Чё там в Scala?**
>
> Ничего интересного, в Scala инкрементов нет. Компилятор скажет, что нет метода ++ для Int. Но если очень захотеть, его, конечно, можно определить.

#### 2\. Одобренный способ

```kotlin
val foo: Int? = null
val bar = foo!! + 5
```

> **Что хотели, то и получили**
>
> Exception in thread «main» kotlin.KotlinNullPointerException

В документации говорится, что так делать стоит только если вы очень хотите получить NullPointerException. Это хороший метод выстрелить себе в ногу: !! режет глаз и при первом взгляде на код все понятно. Разумеется, использование !! предполагается тогда, когда до этого вы проверили значение на null и smart cast по какой-нибудь причине не сработал. Или когда вы почему-то уверены, что там не может быть null.

> **Чё там в Scala?**
>
> ```scala
> val foo: Option[Int] = None
> val bar = foo.get + 5
> ```
>
> > **Что хотели, то и получили**
> >
> > Exception in thread «main» java.util.NoSuchElementException: None.get

#### 3\. Переопределение invoke()

Начнем с простого: что делает этот кусок кода и какой тип у a?

```kotlin
class A(){...}
val a = A()
```

> **На глупый вопрос - глупый ответ**
>
> Правильно, создает новый объект типа A, вызывая конструктор по умолчанию.

А здесь что будет?

```kotlin
class В private constructor(){...}
val b = B()
```

> **Ну, наверно, ошибка компиляции будет...**
>
> А вот и нет!
>
> ```kotlin
> class B private constructor(){
>     var param = 6
>
>     constructor(a: Int): this(){
>         param = a
>     }
>
>     companion object{
>         operator fun invoke() = B(7)
>     }
> }
> ```
>
> Для класса может быть определена фабрика. А если бы она была в классе A, то там все равно вызывался бы конструктор.

Теперь вы ко всему готовы:

```kotlin
class С private constructor(){...}
val c = C()
```

> **Тут создается объект класса С через фабрику, определенную в объекте-компаньоне класса С.**
>
> Конечно же нет!
>
> ```kotlin
>     class C private constructor(){
>         ...
>         companion object{
>             operator fun invoke() = A(9)
>         }
>     }
> ```
>
> У переменной c будет тип A. Заметьте, что A и С не связаны родственными узами.

**Полный код**

> ```kotlin
> class A(){
>     var param = 5
>
>     constructor(a: Int): this(){
>         param = a
>     }
>
>     companion object{
>         operator fun invoke()= A(10)
>     }
> }
>
> class B private constructor(){
>     var param = 6
>
>     constructor(a: Int): this(){
>         param = a
>     }
>
>     companion object{
>         operator fun invoke() = B(7)
>     }
> }
>
> class C private constructor(){
>     var param = 8
>
>     constructor(a: Int): this(){
>         param = a
>     }
>
>     companion object{
>         operator fun invoke() = A(9)
>     }
> }
>
> class D(){
>     var param = 10
>
>     private constructor(a: Int): this(){
>         param = a
>     }
>
>     companion object{
>         operator fun invoke(a: Int = 25) = D(a)
>     }
> }
>
> fun main(args: Array<String>) {
>     val a = A()
>     val b = B()
>     val c = C()
>     val d = D()
>     println("${a.javaClass}, ${a.param}")
>     println("${b.javaClass}, ${b.param}")
>     println("${c.javaClass}, ${c.param}")
>     println("${d.javaClass}, ${d.param}")
> }
> ```
>
> Результат выполнения:
>
> class A, 5
> class B, 7
> class A, 9
> class D, 10

К сожалению, придумать короткий пример, где у вас реально все поломается, я не смог. Но пофантазировать немного можно. Если вы вернете левый класс, как в примере с классом C, то скорее всего, компилятор вас остановит. Но если вы никуда не передаете объект, то можно сымитировать утиную типизацию, как в примере. Ничего криминального, но человек, читающий код, может сойти с ума и застрелиться, если у него не будет исходника класса.

Если у вас есть наследование и функции для работы с базовым классом (Animal), а invoke() от одного наследника (Dog) вернет вам другого наследника (Duck), то тогда при проверке типов (Animal as Dog) вы можете накрякать себе беду.

> **Чё там в Scala?**
>
> В Scala проще — есть new, который всегда вызывает конструктор. Если не будет new, то всегда вызывается метод apply у компаньона (который тоже может вернуть левый тип). Разумеется, если что-то вам не доступно из-за private, то компилятор ругнется. Все то же самое, только очевиднее.

#### 4\. lateinit

```kotlin
class SlowPoke(){
    lateinit var value: String

    fun test(){
        if (value == null){ //компилятор здесь говорит, что проверка не нужна (и правильно делает)
            println("null")
            return
        }
        if (value == "ololo")
            println("ololo!")
        else
            println("alala!")
    }
}
SlowPoke().test()
```

> **Результат предсказуем**
>
> Exception in thread «main» kotlin.UninitializedPropertyAccessException: lateinit property value has not been initialized

> **А как правильно?**
>
> ```kotlin
> class SlowBro(){
>     val value: String? = null
>
>     fun test(){
>         if (value == null) {
>             println("null")
>             return
>         }
>         if (value == "ololo")
>             println("ololo!")
>         else
>             println("alala!")
>     }
> }
> SlowBro().test()
> ```
>
> **Результат**
>
> null

Я бы сказал, что это тоже одобренный способ, но при чтении кода это неочевидно, в отличие от !!. В документации немного завуалированно говорится, что, мол, проверять не надо, если что, мы кинем тебе Exception. По идее, этот модификатор используется тогда, когда вы точно уверены, что поле будет инициализированно кем-то другим. То есть никогда. По моему опыту, все поля, которые были lateinit, рано или поздно стали Nullable. Неплохо это поле вписалось в контроллер JavaFX приложения, где Gui грузится из FXML, но даже это «железобетонное» решение было свергнуто после того, как появился альтернативный вариант без пары кнопок. Один раз так получилось, что в SceneBuilder изменил fx:id, а в коде забыл. В первые дни кодинга на Kotlin немного взбесило, что нельзя сделать lateinit Int. Я могу придумать, почему так сделали, но сомневаюсь, что совсем нет способа обойти эти причины (читай: сделать костыль).

> **Чё там в Scala?**
>
> А там аналога lateinit как такового и нет. По крайней мере, я не обнаружил.

#### 5\. Конструктор

> ```kotlin
> class IAmInHurry(){
>     val param = initSecondParam()
>     /*tons of code*/
>     val twentySecondParam = 10
>     /*tons of code*/
>     fun initSecondParam(): Int{
>         println("Initializing by default with $twentySecondParam")
>         return twentySecondParam
>     }
>
> }
> class IAmInHurryWithStrings(){
>     val param = initSecondParam()
>     /*tons of code*/
>     val twentySecondParam = "Default value of param"
>     /*tons of code*/
>     fun initSecondParam(): String{
>         println("Initializing by default with $twentySecondParam")
>         return twentySecondParam
>     }
> }
> fun main(args: Array<String>){
>     IAmInHurry()
>     IAmInHurryWithStrings()
> }
> ```

> **Результат**
>
> Initializing by default with 0
> Initializing by default with null

Все просто — к полю идет обращение до того, как оно было инициализировано. Видимо, тут стоит немного доработать компилятор. По идее, если вы пишете код хорошо, такая проблема у вас не должна возникнуть, но всякое бывает, не с потолка же я взял этот пример (коллега себе так выстрелил в ногу, случайно через цепочку методов в редко срабатывающем коде вызвал поле, которое было не инициализировано).

> **Чё там в Scala?**
>
> Все то же самое.
>
> object Initializer extends App{
>   class IAmInHurry(){
>     val param = initSecondParam()
>     /*tons of code*/
>     val twentySecondParam = 10
>     /*tons of code*/
>     def initSecondParam(): Int = {
>       println(s"Initializing by default with $twentySecondParam")
>       twentySecondParam
>     }
>
>   }
>
>   class IAmInHurryWithStrings(){
>     val param = initSecondParam()
>     /*tons of code*/
>     val twentySecondParam = "Default value of param"
>     /*tons of code*/
>     def initSecondParam(): String = {
>       println(s"Initializing by default with $twentySecondParam")
>       twentySecondParam
>     }
>
>   }
>
>   override def main(args: Array[String]){
>     new IAmInHurry()
>     new IAmInHurryWithStrings()
>   }
> }
>
> **Результат**
>
> Initializing by default with 0
> Initializing by default with null

#### 6\. Взаимодействие с Java

Для выстрела тут простор достаточно большой. Очевидное решение — считать все, что пришло из Java, Nullable. Но тут есть долгая и поучительная [история](http://blog.jetbrains.com/kotlin/2015/04/upcoming-change-more-null-safety-for-java/). Как я понял, она связана в основном с шаблонами, наследованием, и цепочкой Java-Kotlin-Java. И при таких сценариях приходилось делать много костылей, чтобы заработало. Поэтому решили от идеи «все Nullable» отказаться.
Но вроде как один из основных сценариев — свой код пишем на Kotlin, библиотели берем Java (как видится мне, простому крестьянину-кодеру). И при таком раскладе, лучше безопасность в большей части кода и явные костыли в небольшой части кода, которые видно, чем «красиво и удобно» + внезапные грабли в рантайме (или яма с кольями, как повезет). Но у разработчиков другое [мнение](https://habrahabr.ru/company/JetBrains/blog/277573/#comment_8784731):

> Одна из основных причин была в том, что писать на таком языке было неудобно, а читать его — неприятно. Повсюду вопросительные и восклицательные знаки, которые не очень-то помогают из-за того, что расставляются в основном, чтобы удовлетворить компилятор, а не чтобы корректно обработать случаи, когда выражение вычисляется в null. Особенно больно в случае дженериков: например, Map<String?, String?>?..

Сделаем небольшой класс на Java:

```java
public class JavaCopy {
    private String a = null;

    public JavaCopy(){};

    public JavaCopy(String s){
        a = s;
    }

    public String get(){
        return a;
    }
}
```

И попробуем его вызвать из Kotlin:

```kotlin
fun printString(s: String) {
    println(s)
}

val j1 = JavaCopy()
val j1Got = j1.get()
printString(j1Got)
```

> **Результат**
>
> Exception in thread «main» java.lang.IllegalStateException: j1Got must not be null

Тип у j1 — String! и исключение мы получим только тогда, когда вызовем printString. Ок, давайте явно зададим тип:

```kotlin
val j2 = JavaCopy("Test")
val j3 = JavaCopy(null)

val j2Got: String = j2.get()
val j3Got: String = j3.get()

printString(j2Got)
printString(j3Got)
```

> **Результат**
>
> Exception in thread «main» java.lang.IllegalStateException: j3.get() must not be null

Все логично. Когда мы явно указываем, что нам нужен NotNullable, тогда и ловим исключение. Казалось бы, указывай у всех переменных Nullable, и все будет хорошо. Но если делать так:

```kotlin
printString(j2.get())
```

то ошибку вы можете обнаружить нескоро.

> **Чё там в Scala?**
>
> Никаких гарантий, NPE словить можно элементарно. Решение — оборачивать все в Option, у которого, напомню, есть хорошее свойство, что Option(null) = None. С другой стороны, тут нет иллюзий, что java interop безопасен.

#### 7\. infix нотация и лямбды

Сделаем цепочку из методов и вызовем ее:

```kotlin
    fun<R> first(func: () -> R): R{
        println("calling first")
        return func()
    }

    infix fun<R, T> R.second(func: (R) -> T): T{
        println("calling second")
        return func(this)
    }

    first {
        println("calling first body")
    }
    second {
        println("calling second body")
    }
```

**Результат**

```kotlin
calling first
calling first body
Oops!
calling second body
```

Подождите-ка... тут какая-то подстава! И правда, «забыл» один метод вставить:

```kotlin
fun<T> second(func: () -> T): T{
    println("Oops!")
    return func()
}
```

И чтобы заработало «как надо», нужно было написать так:

```kotlin
first {
    println("calling first body")
} second {
    println("calling second body")
}
```

> **Результат**
>
> calling first
> calling first body
> calling second
> calling second body

Всего один перенос строки, который легко при переформатировании удалить/добавить переключает поведение. Основано на реальных событиях: была цепочка методов «сделай в background» и «потом сделай в ui треде». И был метод «сделай в ui» с таким же именем.

> **Чё там в Scala?**
>
> Синтаксис немного отличается, поэтому так просто тут себе не выстрелишь:
>
> ```scala
> object Infix extends App{
>   def first[R](func: () => R): R = {
>     println("calling first")
>     func()
>   }
>
>   implicit class Second[R](val value: R) extends AnyVal{
>     def second[T](func: (R) => T): T = {
>       println("calling second")
>       func(value)
>     }
>   }
>
>   def second[T](func: () => T): T = {
>     println("Oops!")
>     func()
>   }
>
>   override def main(args: Array[String]) {
>     first { () =>
>       println("calling first body")
>     } second { () => //<--------type mismach
>       println("calling second body")
>     }
>   }
> }
> ```
>
> Зато, пытаясь подогнать скаловский код хотя бы для неочевидности засчет implicit/underscore, я взорвал все вокруг.
>
> > **Осторожно! Кровь, кишки и расчлененка...**
> >
> >
> >
> >     object Infix2 extends App{
> >       def first(func: (Unit) => Unit): Unit = {
> >         println("calling first")
> >         func()
> >       }
> >
> >       implicit class Second(val value: Unit) extends AnyVal{
> >         def second(func: (Unit) => Unit): Unit = {
> >           println("calling second")
> >           func(value)
> >         }
> >       }
> >
> >       def second(func: (Unit) => Unit): Unit = {
> >         println("Oops!")
> >         func()
> >       }
> >
> >       override def main(args: Array[String]) {
> >         first { _ =>
> >           println("calling first body")
> >         } second { _ =>
> >           println("calling second body")
> >         }
> >       }
> >     }
> >
> > И результат:
> >
> > ```scala
> > Exception in thread "main" java.lang.VerifyError: Operand stack underflow
> > Exception Details:
> >   Location:
> >     Infix2$Second$.equals$extension(Lscala/runtime/BoxedUnit;Ljava/lang/Object;)Z @40: pop
> >   Reason:
> >     Attempt to pop empty stack.
> >   Current Frame:
> >     bci: @40
> >     flags: { }
> >     locals: { 'Infix2$Second/pre>, 'scala/runtime/BoxedUnit', 'java/lang/Object', 'java/lang/Object', integer }
> >     stack: { }
> >   Bytecode:
> >     0000000: 2c4e 2dc1 0033 9900 0904 3604 a700 0603
> >     0000010: 3604 1504 9900 4d2c c700 0901 5701 a700
> >     0000020: 102c c000 33b6 0036 57bb 0038 59bf 3a05
> >     0000030: b200 1f57 b200 1fb2 001f 57b2 001f 3a06
> >     0000040: 59c7 000c 5719 06c6 000e a700 0f19 06b6
> >     0000050: 003c 9900 0704 a700 0403 9900 0704 a700
> >     0000060: 0403 ac
> >   Stackmap Table:
> >     append_frame(@15,Object[#4])
> >     append_frame(@18,Integer)
> >     same_frame(@33)
> >     same_locals_1_stack_item_frame(@46,Null)
> >     full_frame(@77,{Object[#2],Object[#27],Object[#4],Object[#4],Integer,Null,Object[#27]},{Object[#27]})
> >     same_frame(@85)
> >     same_frame(@89)
> >     same_locals_1_stack_item_frame(@90,Integer)
> >     chop_frame(@97,2)
> >     same_locals_1_stack_item_frame(@98,Integer)
> >
> >     at Infix2$.main(Infix.scala)
> > ```

#### 8\. Перегрузка методов и it

Это, скорее, метод подгадить другим. Представьте, что вы пишите библиотеку, и в ней есть функция

```kotlin
fun applier(x: String, func: (String) -> Unit){
    func(x)
}
```

Разумеется, народ ее использует довольно прозрачным способом:

```kotlin
applier ("arg") {
    println(it)
}
applier ("no arg") {
    println("ololo")
}
```

Код компилируется, работает, все довольны. А потом вы добавляете метод

```kotlin
fun applier(x: String, func: () -> Unit){
    println("not applying $x")
    func()
}
```

И чтобы компилятор не ругался, пользователям придется везде отказаться от it (читай: переписать кучу кода):

```kotlin
applier ("arg") { it -> //FIXED
    println(it)
}
applier ("no arg") { -> //yes, explicit!
    println("ololo")
}
```

Хотя, теоретически, компилятор мог бы и угадать, что если есть it, то это лямбда с 1 входным аргументом. Думаю, что с развитием языка и компилятор поумнеет, и этот пункт — временный.

> **Чё там в Scala?**
>
> Без аргументов придется явно указать, что это лямбда. А при добавлении нового метода поведение не изменится.
>
> ```scala
> object Its extends App{
>   def applier(x: String, func: (String) => Unit){
>     func(x)
>   }
>
>   def applier(x: String, func: () => Unit){
>     println("not applying $x")
>     func()
>   }
>
>   override def main(args: Array[String]) {
>     applier("arg", println(_))
>     applier("no arg", _ => println("ololo"))
>   }
> }
> ```

#### 9\. Почему не стоит думать о Nullable как об Option

Пусть у нас есть обертка для кэша:

```kotlin
class Cache<T>(){
    val elements: MutableMap<String, T> = HashMap()

    fun put(key: String, elem: T) = elements.put(key, elem)

    fun get(key: String) = elements[key]
}
```

И простой сценарий использования:

```kotlin
val cache = Cache<String>()
cache.put("foo", "bar")

fun getter(key: String) {
    cache.get(key)?.let {
        println("Got $key from cache: $it")
    } ?: println("$key is not in cache!")
}

getter("foo")
getter("baz")
```

> **Результат довольно предсказуем**
>
> Got foo from cache: bar
> baz is not in cache!

Но если мы вдруг захотим к кэше хранить Nullable...

```kotlin
val cache = Cache<String?>()
cache.put("foo", "bar")

fun getter(key: String) {
    cache.get(key)?.let {
        println("Got $key from cache: $it")
    } ?: println("$key is not in cache!")
}

getter("foo")
getter("baz")

cache.put("IAmNull", null)
getter("IAmNull")
```

> **То получится не очень хорошо**
>
> Got foo from cache: bar
> baz is not in cache!
> IAmNull is not in cache!

Зачем хранить null? Например, чтобы показать, что результат не вычислим. Конечно, тут было бы правильнее использовать Option или Either, но, к сожалению, ни того, ни другого в стандартной библиотеке нет (но есть, например, в [funKTionale](https://github.com/MarioAriasC/funKTionale/wiki)). Более того, как раз при реализации Either, я наступил на грабли этого пункта и предыдущего. Решить эту проблему с «двойным Nullable» можно, например, возвратом Pair или специального data class.

> **Чё там в Scala?**
>
> Никто не запретит сделать Option от Option. Надеюсь, понятно, что так все будет хорошо. Да и с null тоже:
>
> ```scala
> object doubleNull extends App{
>   class Cache[T]{
>     val elements =  mutable.Map.empty[String, T]
>
>     def put(key: String, elem: T) = elements.put(key, elem)
>
>     def get(key: String) = elements.get(key)
>   }
>
>   override def main(args: Array[String]) {
>     val cache = new Cache[String]()
>     cache.put("foo", "bar")
>
>     def getter(key: String) {
>       cache.get(key) match {
>         case Some(value) => println(s"Got $key from cache: $value")
>         case None => println(s"$key is not in cache!")
>       }
>     }
>
>     getter("foo")
>     getter("baz")
>
>     cache.put("IAmNull", null)
>     getter("IAmNull")
>   }
> ```
>
>
> > **Все хорошо**
> >
> > Got foo from cache: bar
> > baz is not in cache!
> > Got IAmNull from cache: null

#### 10\. Объявление методов

Бонус для тех, кто раньше писал на Scala. Спонсор данного пункта — [lgorSL](https://habrahabr.ru/users/lgorsl/).
[Цитирую:](https://habrahabr.ru/post/277479/#comment_8779645)

> ...
> Или, например, синтаксис объявления метода:
> В scala: def methodName(...) = {...}
> В kotlin возможны два варианта — как в scala (со знаком =) и как в java (без него), но эти два способа объявления неэквивалентны друг другу и работают немного по-разному, я однажды кучу времени потратил на поиск такой «особенности» в коде.
> ...

> Я подразумевал следующее:
>
> fun test(){ println(«it works») }
> fun test2() = println(«it works too»)
> fun test3() = {println(«surprise!»)}
>
> Чтобы вывести «surprise», придётся написать test3()(). Вариант вызова test3() тоже нормально компилируется, только сработает не так, как ожидалось — добавление «лишних» скобочек кардинально меняет логику программы.
>
> Из-за этих граблей переход со скалы на котлин оказался немного болезненным — иногда «по привычке» в объявлении какого-нибудь метода пишу знак равенства, а потом приходится искать ошибки.

#### Заключение

На этом список наверняка не исчерпывается, поэтому делитесь в комментариях, как вы шли дорогой приключений, но потом что-то пошло не так...
У языка много положительных черт, о которых вы можете прочитать на [официальном сайте](http://kotlinlang.org/), в [статьях](https://habrahabr.ru/post/277479/) [на](https://habrahabr.ru/post/268463/) [хабре](https://habrahabr.ru/post/274997/) и еще много где. Но лично я не согласен с некоторыми архитектурными решениями (классы final by default, java interop) и иногда чувствуется, что языку нехватает единообразия, консистентности. Кроме примера с lateinit Int приведу еще два. Внутри блоков let используем it, внутри with — this, а внутри run, [который является комбинацией let и this](http://beust.com/weblog/2015/10/30/exploring-the-kotlin-standard-library/) что надо использовать? А у класса String! можно вызвать методы isBlank(), isNotBlank(), isNullOrBlank(), а «дополняющего» метода вроде isNotNullOrBlank нет:( После Scala нехватает некоторых вещей — Option, Either, matching, каррирования. Но в целом язык оставляет приятное впечатление, надеюсь, что он продолжит достойно развиваться.

P.S. Хабровская подсветка Kotlin хромает, надеюсь, что администрация [habrahabr](https://habrahabr.ru/users/habrahabr/) это когда-нибудь поправит...

#### UPD: Выстрелы от комментаторов (буду обновлять)

[Неочевидный приоритет оператора elvis](https://habrahabr.ru/post/278169/#comment_8786835). Автор — [senia](https://habrahabr.ru/users/senia/).
