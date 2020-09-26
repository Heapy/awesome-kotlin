
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

Easy to use. This is one of a main tasks to solve then new tool is created. For the world of DSL this
mostly mean it should be easy to a end-user to use the tool and to be able to run the tool within
a continuous integration build easily.

I found an elegant way to fiddle a DSL tasks into Gradle to make it easy to use. Next I'll cover an example
of [TeamCity2DSL](https://github.com/jonnyzzz/TeamCity2DSL) where I implemented this approach.

Domain description
------------------

I will not cover the domain where [TeamCity2DSL](https://github.com/jonnyzzz/TeamCity2DSL) is
applied. This deserves a dedicated post(s) (link will be included here).
All we need to know about TeamCity2DSL here are

- it provides a way to describe build settings with [Kotlin](https://kotlinlang.org/) DSL
- the DSL is executed to generate XML settings that TeamCity understands
- it also generates DSL from existing XML settings from TeamCity

Here goes tricks one need to handle to use the TeamCity2DSL

* download TeamCity2DSL classes
* have Kotlin sources with DSL complied
* allow an IDE to be used to author/edit DSL code

This is vital to provide as easy as possible way to run those tasks. This is where our Gradle plugin is used.

TeamCity2DSL Gradle Plugin
--------------------------

The plugin does the following set of tricks

* it setups project repositories and dependencies
* setups dependency on Kotlin runtime and compiler
* declares `dsl2xml` and `xml2dsl` tasks
* adds DSL generation output folder as Kotlin sources
* introduces a dependency on compilation from `dsl2xml` task


A Gradle Plugin Usage Example
-----------------------------

This is `build.gradle` script that is only required to have both TeamCity2DSL tasks (`dsl2xml` and `xml2dsl`) supported

```groovy
buildscript {
  repositories {
    jcenter()
    mavenCentral()
    maven { url "https://dl.bintray.com/jonnyzzz/maven" }
  }

  dependencies {
    classpath 'org.jonnyzzz.teamcity.dsl:gradle-plugin:<PLUGIN VERSION>'
  }
}

apply plugin: 'org.jonnyzzz.teamcity.dsl'
```

*NOTE*. Replace `<PLUGIN VERSION>` with the latest version
from the [maven repository](https://bintray.com/jonnyzzz/maven/teamcity2dsl/view)
*NOTE2*. We also assume
[TeamCity project settings XML files](https://confluence.jetbrains.com/display/TCD10/Storing+Project+Settings+in+Version+Control)
are located in a `.teamcity` folder.


IDE Usages
----------

The project opens in IntelliJ IDEA. It detects all dependencies, Kotlin, source roots, library sources, etc.
No specific requirements here. It *Just Works*. And again an easy-to-use pattern is implemented.

Implementation Details
======================

I use [Kotlin](https://kotlinlang.org/) in [TeamCity2DSL](https://github.com/jonnyzzz/TeamCity2DSL).
The Gradle plugin is implemented with Kotlin as well.

The first trick is the plugin itself declares a dependency on
[Kotlin Gradle plugin](https://kotlinlang.org/docs/reference/using-gradle.html). The version of Kotlin
is selected from plugin dependency. This allows to avoid
explicit configuration for Kotlin.
```groovy
project.apply { config ->
  config.plugin("java")
  config.plugin("kotlin")
}
```

The plugin includes DSL dependencies to itself into `compile` configuration. Those jars are predefined and
we make Gradle download them from Maven repository.
```groovy
val dsl2xml = project.tasks.create("dsl2xml", Dsl2Xml::class.java)
dsl2xml.dependsOn(project.tasks.getByName("classes"))
```

Next, we include all `buildScript` block repositories into code repositories. This helps to avoid
duplicates in repositories declaration.
```groovy
project.buildscript.repositories.forEach { project.repositories.add(it) }
```

To add extra source directory we use the following code (that depends on Gradle's Java plugin)

```groovy
println("Adding DSL path to Kotlin source set: ${"$"}{settings.dslPath}")
val sourceSets = project.convention.getPlugin(JavaPluginConvention::class.java).sourceSets

println("Source sets: ${"$"}{sourceSets.names}")
sourceSets.getByName("main").java.srcDir( settings.dslPath!!.path )
```

We know Kotlin plugin checks Java output path for kotlin sources too. So we depend here only on Java plugin,
not on a private API of the Kotlin plugin.

The task implementation uses a custom classloader (with `null` parent) to avoid bothering Gradle's tasks
execution classpath. This is too complicated, from the other hand, to synchronize dependencies
of TeamCity2DSL and Gradle. In the future we may consider running an external processes for better stability.

Finally
=======

We created a Gradle plugin that helps to use a Kotlin DSL.

Everything that is related to the setup and execution of tasks is now packed as a Gradle Plugin
leading to easy-to-use and easy-to-adopt solution.

This pattern could be re-used for other applications.

Feel free to try [TeamCity2DSL](https://github.com/jonnyzzz/TeamCity2DSL) for TeamCity project settings domain.

"""

Article(
  title = "A DSL Workbench with Gradle and Kotlin",
  url = "http://jonnyzzz.com/blog/2016/03/08/gradle-for-dsl/",
  categories = listOf(
    "Gradle",
    "DSL",
    "Teamcity2DSL",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Eugene Petrenko",
  date = LocalDate.of(2016, 3, 8),
  body = body
)
