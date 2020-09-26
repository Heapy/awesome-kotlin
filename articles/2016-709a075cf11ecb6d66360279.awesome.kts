
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
#### Taking Ideas From The Front-End Web

If you’ve done Android, you know how quickly your code can grow in complexity as you build out your app. Java is known for boilerplates, and UI-related code (XML & Java combined) will probably take the most of your code. In this post, I’m proposing new solution to make Android UI development much easier and less time consuming. It encourages patterns that may be new for Android, but it existed for long while in front-end development.

Android SDK has a remarkable UI framework that allows you to build user interfaces in clear and concise way using XML. The layout system is very well thought out, you can build any kind of complex interface using it. But due to the dynamic aspects of user interfaces and the evolving UI design patterns nowadays, building UI becomes more complicated than just writing XML and it will involve a lot of Java & XML writing.

#### **Front-End Web vs Android Development**

When compared to mobile or desktop app development in general, I consider web as the easiest platform for building user interfaces. I was impressed by how the front-end community recently created impressive tools that made front-end development much easier and more disciplined. From Grunt, Gulp, Bower, Babel, ES6 to SASS, Bootstrap to React or Angular. There are also so many good patterns that existed in front-end development. Web existed for more than 20 years, so it’s only natural that they have reached this level of maturity when it comes to building UIs.

Thanks to the brilliant XML layouts in Android, I realized there’s a resemblance between Android and front-end web. You can think of XML as the HTML, and Java as the JavaScript. Except in Android we’re at the point of pre-jQuery, we still don’t have the frameworks that allow us to quickly build and manage UI. Android layout system is very flexible, and it provides great opportunities to create such frameworks. We have to take advantage of that and take it to the next level. The patterns used for front-end development are not exclusive to web, we can apply them on Android.

Just to clarify, Android & Java community has written amazing libraries that also made Android development much easier. Just put Retrofit, RxJava, Realm and Kotlin together to create magic, you will be saving a great amount of time writing code. However there are many other challenges that are yet to be solved. This has motivated me build a UI framework inspired by the front-end web. Today, I finally published my [LatteKit](https://github.com/maannajjar/lattekit) framework and made it public in github. But before checking it out, please let me take you through the the problems the framework should solve.

#### **Identifying The Problem**

As I started mulling the idea of creating UI framework, I needed to identify the problems that needed to be solved by my framework. There must be real problems to solve, I didn’t want this to be just an attempt to copy the web. I tried making a small app in Android & web app in order to help me see the things that made Android more complicated. Below are the major points I found:

**1- Non-streamlined UI development**

From design perspective, your app consists of screens that contain set of UI elements. However, it becomes very complicated when you start building the UI in Android. In Android, there are many different types of components that are directly or indirectly related to building the UI. For example, we have Activity that may contains View widgets (subclasses of android.view.View) and/or Fragments, we also have ListViews and Adapters. Each one of those component has a different way to create and manage, passing data between those components can be very different. Fragments work with Bundles, ListView (and others) require Adapters, Activities need Intents, ..etc. Those components have clear purpose and function, but at the end of the day they all play the same part in building the layout tree. All the internals should be abstracted so the components are used in uniform and streamlined way.

**2- Styling UI components**

Styling UI views are done via XML, but not always. Styling in XML may cause a lot of boilerplate. Sometimes you may end up creating 2–3 xml files to achieve single styling objective. Sometimes you’re forced to subclass a certain View in java. For example, if you need to create a circular ImageView, you will have to subclass it and override the drawing method. In Web those are all done in CSS using simple CSS properties.

**3- View Re-usability and Separation of Concerns**

Many times you will need to group views together that perform specific UI function. You can do that by either using `<include>` tag or by subclassing some ViewGroup class and inflating your xml there. Using `<include>` tag alone is not real solution because you will still have to write related Java code in the user code (e.g. in Fragment or Activity). Subclassed custom Views are difficult to manage (for example, if you want your custom view to have some certain custom attributes you will have to define those attributes in attrs.xml, then you will have to capture them in the constructor by reading AttributeSet.) They introduce more boilerplate that sometimes it’s not worth writing a whole subclass of View, you end up writing most your layout in the fragment or activity. When you decide to refactor, the code will be intermingled and hard to move out to its own class.

**4- Majority of apps contain ListView/RecyclerViews/ViewPager. Writing Adapters is a waste of time**

Adapter based Views are basically container views that show repeated UI elements. In web, all it takes is to write a loop which iterates through your dataset and render the UI elements. In Android (and similarly in iOS), you have to a define new Adapter class that will manually create the UI elements and bind the data. This was done for optimization purposes, Adapters help you recycle re-usable views. I understand the need to use Adapters for optimization, but there’s so much repeated pattern when writing Adapters that can be abstracted and make using ListViews (and others) easier.

#### Designing The Solution

I’ll present below my attempt to solve the problems above. My goal is not to create a new UI toolkit, but rather build a framework on top of Android layout system. The framework will manage views behind the scene and abstract many aspects of the underlying Android API. I established some general principles that I should follow while designing my framework

**1- The framework shouldn’t introduce new complicated concepts to Android developers**

It should serve a single purpose: allow you to build the UI and get out of the way. No complicated APIs to learn. Android developer should find it very easy to adapt to the framework.

**2- The framework should provide uniform and concise way to build UI**

There should be only one way to build a layout. The framework shouldn’t provide different and confusing alternatives to build certain UI elements.

**3- Defining a View should only mean writing one file, and with minimum boilerplate**

You shouldn’t be jumping between xml and java files.

**4- The framework should use proven patterns to build UIs**

I shouldn’t be creating my own pattern to build UIs, there are tons of proven patterns that already worked and I don’t need to get too creative and invent something totally new.

I started testing different frameworks used for front-end development. Two popular frameworks that caught my attention are Angular and React. I was very impressed by React and its simplicity. It doesn’t introduce new concepts and it has low learning curve. All you need to know is HTML & JavaScript and basic details about the lifecycle of React components. It introduces the concept of virtual DOM, and provides a way to bind data. You can read more about it here [https://facebook.github.io/react/](https://facebook.github.io/react/). React model also fits perfectly with my 4 principles. They do have React Native which is framework to build for mobile including Android. However, it introduces new concepts and it relies on JavaScript which is strange to Android. Also, you will have to write bridges between Java & JavaScript if you want to use Android’s API.

**Plain Java Won’t Work**

While designing my framework. I decided that I shouldn’t be using plain Java for many reasons. First, it will introduce many boilerplates that will defeat the purpose of the framework. Second, for technical reasons I needed some features that Java can’t offer.

Luckily there’s Kotlin, which is becoming a rising start in Android development. Kotlin is modern JVM language that provides modern language features which are missing in Java. It heavily reduces the boilerplate code and it’s interoperable with Java. It works great with Android and it’s supported in Android Studio (Kotlin was created by JetBrains, the maker of IntelliJ IDEA).

#### **And LatteKit Was Born...**

There’s so much to talk about LatteKit and how I built it. The concept should be clear though. If you know minimal Kotlin & CSS with Android knowledge, you should be able to pick up the concept quickly. I created a quick example & documentation in the github repository. There is also a full sample which shows how much lines of code you will save when using the library, it’s really a lot. Available at:

[https://github.com/maannajjar/lattekit](https://github.com/maannajjar/lattekit)

Please take a look, try it out and let me know what you think. Keep in mind that this a first release, the core functionality is complete but there are many things left to do in the roadmap.

Below is a quick sample I built using the library , you can view the source code in the github repo which shows the minimal line of codes needed.

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*fULW0kZcUOPcPOmm5QMaBQ.png)

I’ll dedicate another post about the patterns to use with LatteKit & Kotlin, which will make building Android apps very quickly and with the minimal effort. I’m happy with what I have accomplished so far, I’m also not taking full credit for the idea because it was inspired by React which proved to work really well in the web.
"""

Article(
  title = "Making Android Development Easier",
  url = "https://medium.com/@maannajjar/progressive-android-ui-development-28d281dbe21b#.g8c7xq81a",
  categories = listOf(
    "Android",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Maan Najjar",
  date = LocalDate.of(2016, 4, 11),
  body = body
)
