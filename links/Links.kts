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
      name = "Slack (10000+ users)"
      href = "https://kotlinslack.herokuapp.com/"
    }
    link {
      name = "Public chat archive of Kotlin's Slack"
      href = "http://kotlinlang.slackarchive.io/"
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
      name = "LinkedIn: Kotlin Developers (Join!)"
      href = "https://www.linkedin.com/groups/7417237/profile"
    }
    link {
      name = "Kotlin - Google+"
      href = "https://plus.google.com/communities/104597899765146112928"
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
  }
  subcategory("Books and Courses") {
    link {
      name = "Kotlin in Action - Dmitry Jemerov, Svetlana Isakova"
      href = "https://manning.com/books/kotlin-in-action"
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
      name = "Android Development with Kotlin - Marcin Moskala, Igor Wojda"
      desc = "Learn how to make Android development much faster using a variety of Kotlin features, from basics to advanced, to write better quality code"
      href = "https://www.packtpub.com/application-development/android-development-kotlin"
    }
    link {
      name = "Mastering Android Development with Kotlin - Miloš Vasić"
      desc = "Master Android development using a variety of Kotlin features"
      href = "https://www.packtpub.com/application-development/mastering-android-development-kotlin"
    }
  }
}
