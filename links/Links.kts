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
      name = "Slack (6700+ users)"
      href = "http://kotlinslackin.herokuapp.com/"
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
  }
  subcategory("Resources") {
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
  }
}

