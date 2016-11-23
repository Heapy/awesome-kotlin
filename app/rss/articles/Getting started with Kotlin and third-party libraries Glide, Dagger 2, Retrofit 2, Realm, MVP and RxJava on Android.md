---
title: 'Getting started with Kotlin and third-party libraries Glide, Dagger 2, Retrofit 2, Realm, MVP and RxJava on Android'
url: http://steelkiwi.com/blog/getting-started-kotlin-libraries-Glide-Dagger/
categories:
    - Kotlin
    - Android
author: Yaroslav Polyakov
date: Nov 17, 2016 11:04
---
It’s not a secret to Android developers all around the globe that IT community has been trying to find a distinct replacement of Java. Until 2011 before Kotlin creation was announced the most suitable candidate was Scala. Kotlin source code was published in 2012, and in 2016 the 1.0 version was released.

Kotlin was claimed to be a response to massive Java code and Scala low-speed compilation. Today many famous IT companies use Kotlin in their projects. The attention to this language is continuing to grow, as it wins over the developers with its syntax and the fact that Kotlin is supported in IDE by the means of plugin. Even Jake Wharton, recognized Android gospeller, uses Kotlin in the projects of his own thus encouraging Android community to use this language.

Let’s stay abreast of this trend and try Kotlin on practice to see its extensively discussed pros and cons. And also we will learn how to start development of Android applications using Kotlin.

## What does Kotlin hide?

Mostly Kotlin is commended for its briefness and code safety and also for compatibility with Java and flexible usage. It’s hard to list all its advantages, so let’s consider the most interesting ones.

### Kotlin’s Strengths:

* It is [fully compatible](https://github.com/steelkiwi/Getting-started-with-Kotlin/blob/master/app/src/main/java/com/ktoi/toi/model/NewsResponse.java#L12) with Java. It can use all available Java frameworks, libraries and also with separate modules in current projects.

* It has [open source code](https://kotlinlang.org/), so it’s easy to track and determine an issue and if you come up with it, you can always submit it to Kotlin developers.

* Kotlins repository consumes less space than Scala, and adding Kotkin to a project is equal to Google library.

* Starting from Java 6, it can use the major part of Java 7 and some portable elements of Java 8. That’s why it is easily available even if you are facing troubles updating to new JVM version.

* There’s immutability by default.

* It has Higher-Order Functions, i.e. the functions that take functions as parameters.

* Null safety. Variables in Kotlin can’t possess the null value by default unless you denote them.

If the Kotlin advantages are clear, it has disadvantages as well, even though they are not so obvious.

### Weaknesses of Kotlin:

* Kotlin is Java 6 bytecode-oriented and doesn’t use a number of improvements available in Java 8 like invoke-dynamic.
* There can appear issues with annotation processing.
* There are no analogues to plugin-macros or compilers which thus limits the usage of convenient Scala macros or plugins like Checker Framework from Java.
* If you practice joint usage of Java and Kotlin you need to follow certain rules of compatibility.
* You will have to rewrite some proven to be effective solutions for Android, including the architectural ones as Kotlin enables the usage of alternative approach.
* The language is pretty young and hasn’t found any specific niche, however, it is suitable not only for Android but also for backend development.
* The language is pretty green and there are no worked out best practices for solving particular tasks.

Let’s dwell on compatibility and look closely at the example of a simple application development using such popular libraries like:

* [Glide](https://github.com/bumptech/glide)
* [Dagger 2](https://github.com/google/dagger)
* [Retrofit 2](https://github.com/square/retrofit)
* [Realm database](https://realm.io/)
* [RxJava + RxAndroid](https://github.com/ReactiveX/RxAndroid)

The app serves as a good instance of libraries data usage and implementation of MVP architecture:

![A good instance of libraries data usage and implementation of MVP architecture](http://steelkiwi.com/media/markdownx/e76ce8ac-7999-473a-9749-1b90edb5ca24.png)

## Let’s proceed to the development:

![](http://steelkiwi.com/media/markdownx/6bc4f839-5ead-4741-bdf3-1939d728b1ed.png)

After that configure your project. The easiest way to do this is to press Ctrl+Shift+A and find Configure Kotlin in Project item that will appear in autocomplete:

![](http://steelkiwi.com/media/markdownx/49da4de3-6699-4f1b-89f3-3db63ba53500.png)

To convert existing Java classes to Kotlin you need to find the command named Convert Java to Kotlin:

![](http://steelkiwi.com/media/markdownx/505130b1-e437-4019-a4de-928b7170b055.png)

Now let's start the integration of the needed libraries A few words before we start. Some libraries like Dagger 2 require Annotation Processing. Java Annotation Processing is not suitable for Kotlin. That’s why it includes its own Annotation Processing Tool for Kotlin (kapt), here is a great opinion on it.

To activate it you need to add this in the [build.gradle](https://github.com/steelkiwi/Getting-started-with-Kotlin/blob/master/app/build.gradle#L26) file:

    kapt {
        generateStubs = true
    }
    buildscript {
       ext.supportVersion = '25.0.0'
       ext.daggerVersion = '2.7'
       ext.retrofitVersion = '2.1.0'
       ext.rxVersion = '1.2.1'
       repositories {
           mavenCentral()
           jcenter()
       }
       dependencies {
           classpath "io.realm:realm-gradle-plugin:2.1.1"
       }
    }

    dependencies {
       compile fileTree(dir: 'libs', include: ['*.jar'])
       androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
           exclude group: 'com.android.support', module: 'support-annotations'
       })
       compile 'com.android.support:appcompat-v7:25.0.0'
       testCompile 'junit:junit:4.12'
       compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
       compile "com.android.support:cardview-v7:${supportVersion}"
       compile "com.android.support:design:${supportVersion}"

       // Dagger 2
       compile "com.google.dagger:dagger:${daggerVersion}"
       kapt "com.google.dagger:dagger-compiler:${daggerVersion}"
       provided "org.glassfish:javax.annotation:3.1.1"

       //Retrofit 2
       compile "com.squareup.retrofit2:retrofit:${retrofitVersion}"
       compile "com.squareup.retrofit2:adapter-rxjava:${retrofitVersion}"
       compile "com.squareup.retrofit2:converter-gson:${retrofitVersion}"

       compile 'com.google.code.gson:gson:2.8.0'

       compile "io.reactivex:rxjava:${rxVersion}"
       compile "io.reactivex:rxandroid:${rxVersion}"

       compile 'com.github.bumptech.glide:glide:3.7.0'

     }

Then we create [inheriting class](https://github.com/steelkiwi/Getting-started-with-Kotlin/blob/master/app/src/main/java/com/ktoi/toi/shared/AppDelegate.kt#L10) from Application and configure Realm and dependencies graph for Dagger 2 developed by Google:

    class AppDelegate : Application() {

       var injector: AppInjector? = null

       @Singleton
       @Component(modules = arrayOf(NewsModule::class))
       interface AppInjector {

           fun inject(activity: MainActivity)

       }

       override fun onCreate() {
           super.onCreate()
           injector = DaggerAppDelegate_AppInjector.builder().build()

           Realm.init(this)
           val config = RealmConfiguration.Builder().build()
           Realm.setDefaultConfiguration(config)
       }
    }

To work with HTTP we use Retrofit. We need to create an [interface](https://github.com/steelkiwi/Getting-started-with-Kotlin/blob/master/app/src/main/java/com/ktoi/toi/shared/NewsApiInterface.kt#L8) with one method that will receive news feed:

    interface NewsApiInterface {
       @GET("/feeds/newsdefaultfeeds.cms?feedtype=sjson")
       fun getNews(): Observable<NewsResponse>
    }

We create [NewsModule class](https://github.com/steelkiwi/Getting-started-with-Kotlin/blob/master/app/src/main/java/com/ktoi/toi/shared/NewsModule.kt#L18) wherein the injected objectives will be created:

    @Module
    class NewsModule {

       @Provides
       @Singleton
       fun provideNewsPresenter(): NewsPresenter {
           return NewsPresenter()
       }

       @Provides
       @Singleton
       internal fun provideNewApiInterface(): NewsApiInterface {
           val retrofit = Retrofit.Builder()
                   .baseUrl(Constants.NEWS_ENDPOINT)
                   .addConverterFactory(GsonConverterFactory.create())
                   .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                   .build()
           return retrofit.create(NewsApiInterface::class.java)
       }
    }

In [NewPresenter class](https://github.com/steelkiwi/Getting-started-with-Kotlin/blob/master/app/src/main/java/com/ktoi/toi/presenter/NewsPresenter.kt#L16) we will use RxAndroid and Realm for [news feed processing and caching](https://github.com/steelkiwi/Getting-started-with-Kotlin/blob/master/app/src/main/java/com/ktoi/toi/presenter/NewsPresenter.kt#L32):

    subscription = mNewsApiInterface!!
           .getNews()
           .map { it.newsItem }
           .flatMap({ items ->
               Realm.getDefaultInstance().executeTransaction({ realm ->
                   realm.delete(NewsItem::class.java)
                   realm.insert(items)
               })
               Observable.just(items)
           })
           .onErrorResumeNext { throwable ->
               val realm = Realm.getDefaultInstance()
               val items = realm.where(NewsItem::class.java).findAll()
               Observable.just(realm.copyFromRealm(items))
           }
           .observeOn(AndroidSchedulers.mainThread())
           .doOnTerminate { mNewsView?.hideLoading() }
           .subscribe({ mNewsView?.onNewsItemLoaded(it) }, { mNewsView?.onError(it) })

Projects source code is available on [Repository](https://github.com/steelkiwi/Getting-started-with-Kotlin).

### Let’s sum up

All in all, Kotlin is a modern and secure programming language that simplifies Android apps development. It looks like a distinct alternative to Java as it has good documentation and it is simple enough for understanding. We hope that this article helped you to figure out how to create a project on Kotlin and integrate the needed libraries.
