
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
Hi!

This is my first blog post.

## Intro

Here I will be talking about difference in performance between using XML layouts and [_Anko_](https://github.com/Kotlin/anko) to build UI for _Android_ apps.

As you may know, _Anko_ is a new DSL for Android, written in [_Kotlin_](https://kotlinlang.org/). It helps a lot to develop _Android_ apps using _Kotlin_ lang. _Anko_ provides various handy features, and my favorite is building ui with code. Yeah, you can tell me that we can also build user interface using _Java_, and create all Views in code, but it is ugly and hard to maintain.

## The problem

By default, UI in _Android_ is built using XML layout files. This is inconvenient because it is not typesafe, not null-safe, and it leads to overhead in **cpu** and **ram** usage. It can be insensibly for fast and powerful devices, but low-end devices may suffer from resources deficit. The more Views you have in your xml layout file, the more time it takes to:

* inflate view from XML file
* create view objects
* find views by id with [_findViewById_](http://developer.android.com/intl/ru/reference/android/view/View.html#findViewById(int))

Now imagine, that you need to perform that actions for multiple layouts, for example if you need to display list of items. In this case android will perform every step of those 3, for every list item in your UI. It will be even worse if every list item have more than 5-7 views. More views you have, more time it takes to display them.

By using _Anko_ (or creating Views programmatically, even with _Java_) we can remove 2/3 of that work:

* ~~inflate view from XML file~~
* create view objects
* ~~find views by id with [_findViewById_](http://developer.android.com/intl/ru/reference/android/view/View.html#findViewByIdn(int))~~

Also it is easy to create UI with _Anko_, if you’re familiar with android _Layouts_.

## Benchmark

For benchmark i will use two Activities, with two similar lists of users. I think it is a typical case for _Android_ apps. For every list item i will be using RelativeLayout with 4 views inside:

* user avatar (using [_Fresco_](http://frescolib.org/))
* user name
* user title
* last seen date

After that i will measure [_onCreateViewHolder_](http://developer.android.com/intl/ru/reference/android/support/v7/widget/RecyclerView.Adapter.html#onCreateViewHolder) method execution time for every item.

![device](https://cloud.githubusercontent.com/assets/5869863/14586400/8b257a58-049f-11e6-8e72-14f78753c53b.png)

### Benchmark results

Well, i tested both cases on these devices:

* android emulator on MacBook pro 15 2015
* Meizu mx-5
* THL w8 (low end)
* Nexus 6 Marshmallow
* Meizu mx-4
* Samsung Galaxy S2 Plus
* Samsung Galaxy S3
* Samsung Galaxy J5

All values are in milliseconds, that needed for _onCreateViewHolder_ method to execute inside the RecyclerView adapter.

> Please note that frames on Android are drawing every 16 milliseconds, and if any frame will not be able to draw in that period, it will draw after 32/48/64... ms, and this is called dropped frames. So every time your frame takes >16ms to draw, it is dropped. Also note that your UI thread will have more work than just drawing frames, it need to handle all user input, and execute all of your code too. It means that less milliseconds to draw is better for us.

And here is what i got:

![2016-04-18 17 13 40](https://cloud.githubusercontent.com/assets/5869863/14607128/f77fa9b4-0588-11e6-9fcc-4884ad6d709a.png)

[Macbook XML results](https://cloud.githubusercontent.com/assets/5869863/14587122/984e5338-04b5-11e6-8dd3-f953a0c2d03a.png) * [Macbook Coded results](https://cloud.githubusercontent.com/assets/5869863/14587125/b5fb4332-04b5-11e6-9b59-71c38f5bd2d0.png)

[Meizu MX5 XML results](https://cloud.githubusercontent.com/assets/5869863/14587130/c8315e24-04b5-11e6-84d7-8a943e386691.png) * [Meizu MX5 Coded results](https://cloud.githubusercontent.com/assets/5869863/14587140/0143ddf4-04b6-11e6-98dd-940e89ae141e.png)

[Thl W8 XML results](https://cloud.githubusercontent.com/assets/5869863/14587146/2fe107ae-04b6-11e6-8f25-5bc901d88780.png) * [Thl W8 Coded results](https://cloud.githubusercontent.com/assets/5869863/14587153/42b90c8c-04b6-11e6-8fba-6f8fec4c0b75.png)

[Nexus 5 XML results](https://cloud.githubusercontent.com/assets/5869863/14587159/54031f5a-04b6-11e6-9b18-3ebd6b1e7a90.png) * [Nexus 5 Coded results](https://cloud.githubusercontent.com/assets/5869863/14587160/617e05aa-04b6-11e6-8082-ee802e5ebf4d.png)

[Meizu MX4 XML results](https://cloud.githubusercontent.com/assets/5869863/14587163/75c93db8-04b6-11e6-870c-44dfcda0dc0e.png) * [Meizu MX4 Coded results](https://cloud.githubusercontent.com/assets/5869863/14587167/87feb06c-04b6-11e6-9b90-8c7549554292.png)

[Galaxy S2 Plus XML results](https://cloud.githubusercontent.com/assets/5869863/14607148/10f5da3a-0589-11e6-8493-e81a92dddd38.png) * [Galaxy S2 Plus Coded results](https://cloud.githubusercontent.com/assets/5869863/14607158/1b9d5d32-0589-11e6-8185-db11a263e2ca.png)

[Galaxy J5 XML results](https://cloud.githubusercontent.com/assets/5869863/14602312/e072d2be-056f-11e6-9ec0-2a23799b6911.png) * [Galaxy J5 Coded results](https://cloud.githubusercontent.com/assets/5869863/14602328/f7fb5d84-056f-11e6-86fd-1d0169a0ef45.png)

[Galaxy S3 XML results](https://cloud.githubusercontent.com/assets/5869863/14602338/0bbfc29c-0570-11e6-9189-977387de5c3d.png) * [Galaxy S3 Coded results](https://cloud.githubusercontent.com/assets/5869863/14602353/1d4b2498-0570-11e6-858c-586957d4784b.png)

## Conclusion

As I thought, building User Interface on Android is faster with code, than with XML layout files. And this is true for all kind of devices/sdk’s.

We can see up to **300%** performance boost here.

There is not a big (relatively) boost on high-end devices, but low-end devices can have huge boost using coded ui.

_Should we use coded UI everywhere?_ - **Only if you are using Kotlin, and you have some extra time for optimizations**

_Should we use it if we have large amount of Views into single list item?_ - I would say **YES**

_Should we use it if our customer has low-end device, and says that app is freezing all the time?_ - Answer will be **yes** too.

Source code can be found [HERE](https://github.com/nethergrim/anko_benchmark)

P.S. You can try that benchmark by **yourself**, on your device. Just check the [repo](https://github.com/nethergrim/anko_benchmark)


"""

Article(
  title = "Performance comparison - building Android UI with code (Anko) vs XML Layout.",
  url = "https://nethergrim.github.io/performance/2016/04/16/anko.html",
  categories = listOf(
    "Kotlin",
    "Anko",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Andrew Drobyazko",
  date = LocalDate.of(2016, 4, 16),
  body = body
)
