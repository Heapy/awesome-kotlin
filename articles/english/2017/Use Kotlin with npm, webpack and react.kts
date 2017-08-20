

import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
With Kotlin 1.1, targeting JavaScript in the Kotlin compiler has officially reached production-ready status. Of course, having compiler support is not enough to be able to solve real-life problems, so we continue our work on integrating Kotlin into the larger JavaScript ecosystem.

Today, we’d like to present two new projects: a Gradle plugin integrating Kotlin with npm, webpack and karma, and a full-stack application sample with a Kotlin/JVM backend and a Kotlin/JS frontend built with React.

## Kotlin Frontend Plugin

The [Kotlin frontend plugin](https://github.com/Kotlin/kotlin-frontend-plugin) allows you to build and deploy a Kotlin frontend application using webpack. You can use npm packages as dependencies of your application, and the plugin will take care of downloading them and bundling them into the resulting JS file. The plugin also integrates with Karma, allowing you to run the tests of your application. And for optimal workflow, the plugin supports continuous compilation and hot reload, ensuring that you always see an up-to-date version of your application in the browser.

The [README](https://github.com/Kotlin/kotlin-frontend-plugin/blob/master/README.md) file gives instructions for using the plugin, and the examples directory contains a [simple example](https://github.com/Kotlin/kotlin-frontend-plugin/tree/master/examples/frontend-only) showing how you can apply it in a real project.

## Kotlin React Example

[Thinkter](https://github.com/Kotlin/kotlin-fullstack-sample) is an example of a modern full-stack application built in Kotlin. The backend runs under Jetty and uses [Ktor](https://github.com/kotlin/ktor), a Kotlin Web application framework developed by the Kotlin team. The frontend uses React; a set of React wrappers for Kotlin is [provided as part of the project](https://github.com/Kotlin/kotlin-fullstack-sample/tree/master/frontend/src/org/jetbrains/react). You’re welcome to use the wrappers in your project and adapt them to your own needs. Note that we’re working on evolving the React wrappers internally, and we’re considering releasing them as a separate open-source library.

To see what Kotlin React code looks like, you can check out [one of the components](https://github.com/Kotlin/kotlin-fullstack-sample/blob/master/frontend/src/org/jetbrains/demo/thinkter/NewThoughtComponent.kt) of the application.

Your feedback on these releases is very much welcome! Please file issues on GitHub, stop by the [forums](https://discuss.kotlinlang.org/), or join the #javascript channel on the [Kotlin Slack](http://slack.kotlinlang.org/).
"""

Article(
  title = "Use Kotlin with npm, webpack and react",
  url = "https://blog.jetbrains.com/kotlin/2017/04/use-kotlin-with-npm-webpack-and-react/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Dmitry Jemerov",
  date = LocalDate.of(2017, 4, 18),
  body = body
)
