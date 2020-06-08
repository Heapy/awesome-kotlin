import link.kotlin.scripts.LinkType
import link.kotlin.scripts.LinkType.github
import link.kotlin.scripts.Tags
import link.kotlin.scripts.category
import link.kotlin.scripts.link
import link.kotlin.scripts.subcategory

category("Links") {
  subcategory("Official Links") {
    link {
      name = "JetBrains/kotlin"
      href = "https://github.com/jetbrains/kotlin"
      type = github
      tags = Tags["kotlin"]
    }
    link {
      name = "Home Page"
      href = "http://kotlinlang.org/"
    }
    link {
      name = "Language Reference"
      href = "http://kotlinlang.org/docs/reference/"
    }
    link {
      name = "Slack (21k+ users)"
      href = "http://slack.kotlinlang.org/"
    }
    link {
      name = "Try Kotlin!"
      href = "http://try.kotlinlang.org/"
    }
    link {
      name = "Blog"
      href = "http://blog.jetbrains.com/kotlin/"
    }
    link {
      name = "Issue Tracker"
      href = "https://youtrack.jetbrains.com/issues/KT"
    }
    link {
      name = "Twitter"
      href = "https://twitter.com/kotlin"
    }
    link {
      name = "Kotlin/KEEP"
      desc = "Kotlin Evolution and Enhancement Process"
      href = "https://github.com/Kotlin/KEEP"
      type = github
      tags = Tags["keep", "async", "await"]
    }
    link {
      name = "Forum"
      href = "https://discuss.kotlinlang.org/"
    }
  }
  subcategory("Resources") {
    link {
      name = "Jobs for Kotlin Developers"
      desc = "All job listings require Kotlin skills"
      href = "https://kotlin-jobs.com/"
    }
    link {
      name = "Podcast: Talking Kotlin"
      desc = "A Podcast on Kotlin and more"
      href = "http://talkingkotlin.com/kotlin-at-pinterest-with-christina-lee/"
    }
    link {
      name = "/r/Kotlin"
      href = "https://www.reddit.com/r/Kotlin/"
    }
    link {
      name = "Stackoverflow Documentation on Kotlin"
      href = "http://stackoverflow.com/documentation/kotlin/topics"
      tags = Tags["documentation", "stackoverflow"]
    }
    link {
      name = "Quora Kotlin"
      href = "https://www.quora.com/topic/Kotlin"
    }
    link {
      name = "Trending Kotlin on Github"
      href = "https://github.com/trending?l=kotlin"
    }
    link {
      name = "Antonio Leiva - Android and any other monsters"
      href = "http://antonioleiva.com/"
      type = LinkType.blog
    }
    link {
      name = "Programming Community Curated Resources for learning Kotlin"
      href = "https://hackr.io/tutorials/learn-kotlin"
    }
    link {
      name = "LinkedIn: Kotlin Developers (Join!)"
      href = "https://www.linkedin.com/groups/7417237/profile"
    }
    link {
      name = "From Java To Kotlin"
      href = "https://github.com/fabiomsr/from-java-to-kotlin"
    }
    link {
      name = "dbacinski/Design-Patterns-In-Kotlin"
      desc = "Design Patterns implemented in Kotlin."
      href = "https://github.com/dbacinski/Design-Patterns-In-Kotlin"
      type = github
      tags = Tags["Behavioral Patterns", "Creational Patterns", "Structural Patterns"]
    }
    link {
      name = "Kotlin Cheat Sheet"
      href = "https://speakerdeck.com/agiuliani/kotlin-cheat-sheet"
    }
    link {
      name = "Kotlin Kōans Android app"
      desc = "Learn Kotlin with short coding challenges on your Android device"
      href = "https://play.google.com/store/apps/details?id=me.vickychijwani.kotlinkoans"
    }
    link {
      name = "Kotlin Guide"
      desc = "An introduction to Kotlin"
      href = "https://kotlin.guide"
    }
    link {
      name = "Kotlin Academy"
      desc = "Blog that teach about Kotlin"
      href = "https://blog.kotlin-academy.com/"
    }
     link {
      name = "Kotlin Expertise Blog"
      desc = "A Blog with many articles covering basic and advanced Kotlin topics."
      href = "https://kotlinexpertise.com/"
    }
    link {
      name = "Google's Kotlin Codelab "
      desc = "Build your first Kotlin Android App"
      href = "https://codelabs.developers.google.com/codelabs/build-your-first-android-app-kotlin/"
    }
    link {
      name = "Kotlin Quiz"
      desc = "Are You a Kotlin Expert? Test Your Skills with the Kotlin Quiz!"
      href = "https://kotlinquiz.com"
    }
    link {
      name = "SuperKotlin"
      desc = "A website about Kotlin"
      href = "https://superkotlin.com"
      tags = Tags["blog", "articles", "tutorials", "interviews"]
    }
    link {
      name = "AlexeySoshin/Hands-on-Design-Patterns-with-Kotlin"
      desc = "Code examples for 'Hands-on Design Patterns with Kotlin' book"
      href = "https://github.com/AlexeySoshin/Hands-on-Design-Patterns-with-Kotlin"
      type = github
      tags = Tags["GoF", "concurrency"]
    }
    link {
      name = "tbhaxor/GUIDE-TO-KOTLIN"
      desc = """A practical guide on "Kotlin for Developers""""
      href = "https://github.com/tbhaxor/GUIDE-TO-KOTLIN"
    }
  }
  subcategory("Books and Courses") {
    link {
      name = "Kotlin in Action - Dmitry Jemerov, Svetlana Isakova"
      href = "https://manning.com/books/kotlin-in-action"
    }
    link {
      name = "Functional Programming in Kotlin - Marco Vermeulen, Rúnar Bjarnason, and Paul Chiusano"
      href = "https://www.manning.com/books/functional-programming-in-kotlin"
    }
    link {
      name = "Kotlin for Android Developers - Antonio Leiva"
      href = "https://leanpub.com/kotlin-for-android-developers"
    }
    link {
      name = "Programming Kotlin - Stephen Samuel, Stefan Bocutiu"
      href = "https://www.packtpub.com/application-development/programming-kotlin"
    }
    link {
      name = "Fundamental Kotlin, First Edition"
      desc = "Learn Kotlin quickly from the start"
      href = "http://www.fundamental-kotlin.com/"
    }
    link {
      name = "Kotlin for Java Developers"
      desc = "160-minute Android Course."
      href = "https://teamtreehouse.com/library/kotlin-for-java-developers"
    }
    link {
      name = "Functional Kotlin"
      desc = "Learn Functional Programming in Kotlin from scratch and how to apply Functional Programming with Kotlin to real-life projects with popular libraries like Arrow. By Mario Arias, Rivu Chakraborty"
      href = "https://www.packtpub.com/application-development/functional-kotlin"
    }
    link {
      name = "Reactive Programming in Kotlin - Rivu Chakraborty"
      desc = "Learn how to implement Reactive Programming paradigms with Kotlin, and apply them to Web programming with Spring Framework 5.0 as well as in Android Application Development. By Rivu Chakraborty"
      href = "https://www.packtpub.com/application-development/reactive-programming-kotlin"
    }
    link {
      name = "Kotlin Programming: Next Level Java Development"
      desc = "Learn coding in Kotlin from scratch!"
      href = "https://www.udemy.com/kotlin-course/"
    }
    link {
      name = "Introduction to Kotlin Programming by Hadi Hariri"
      desc = "From Hello World to Interoperability with Java"
      href = "http://shop.oreilly.com/product/0636920052982.do"
    }
    link {
      name = "Advanced Kotlin Programming"
      desc = "From Nested Functions to Asynchronous Programming"
      href = "http://shop.oreilly.com/product/0636920052999.do"
    }
    link {
      name = "Kotlin an Introduction - Caster.io"
      desc = "Kotlin introductory course at Caster.io"
      href = "https://caster.io/courses/introduction-to-kotlin/"
    }
    link {
      name = "Android Development with Kotlin - Marcin Moskala, Igor Wojda"
      desc = "Learn how to make Android development much faster using a variety of Kotlin features, from basics to advanced, to write better quality code"
      href = "https://www.packtpub.com/application-development/android-development-kotlin"
    }
    link {
      name = "Mastering Android Development with Kotlin - Miloš Vasić"
      desc = "Master Android development using a variety of Kotlin features"
      href = "https://www.packtpub.com/application-development/mastering-android-development-kotlin"
    }
    link {
      name = "Building Android Apps with Kotlin: Getting Started - Alex Dunn"
      desc = "Dive into the language set to replace Java while building Android applications. This course will give you a foundation of both Kotlin and Android skills to allow you to build apps faster and cleaner than ever before."
      href = "https://www.pluralsight.com/courses/building-android-apps-kotlin-getting-started"
    }
    link {
      name = "Kotlin Apprentice - Irina Galata, Joe Howard, Richard Lucas & Ellen Shapiro"
      desc = "Beginning programming with Kotlin"
      href = "https://store.raywenderlich.com/products/kotlin-apprentice"
    }
    link {
      name = "Android Apprentice - Darryl Bayliss & Tom Blankenship"
      desc = "Beginning Android programming using Kotlin"
      href = "https://store.raywenderlich.com/products/android-apprentice"
    }
    link {
      name = "Hands-on Design Patterns with Kotlin - Alexey Soshin"
      desc = "Book that covers building scalable applications using traditional, reactive, and concurrent design patterns in Kotlin"
      href = "https://www.amazon.com/Hands-Design-Patterns-Kotlin-applications/dp/1788998014"
    }
    link {
      name = "Kotlin Bootcamp for Programmers - Google"
      desc = "Language fundamentals for developers"
      href = "https://www.udacity.com/course/kotlin-bootcamp-for-programmers--ud9011"
    }
    link {
      name = "Developing Android Apps with Kotlin - Google"
      desc = "Create concise, secure and performant apps with Kotlin"
      href = "https://www.udacity.com/course/developing-android-apps-with-kotlin--ud9012"
    }
    link {
      name = "Kotlin for Android Developers"
      desc = "Convert an Android app from Java to Kotlin"
      href = "https://www.udacity.com/course/kotlin-for-android-developers--ud888"
    }
    link {
      name = "Kotlin for Java Developers - JetBrains"
      desc = "Detailed Kotlin introductory course for Java developers"
      href = "https://www.coursera.org/learn/kotlin-for-java-developers"
    }
    link {
      name = "Kotlin Online Courses at Classpert"
      desc = "Over 80 Kotlin Online Courses - Classpert Online Course and MOOC Search"
      href = "https://classpert.com/kotlin-programming"
    }    
  }
}
