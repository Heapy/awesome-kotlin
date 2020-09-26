
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
So I recently got my hands on Kotlin and it's been great so far.

However one of the things that the IDEA kotlin plugin doesn't do<sup id="fnref:1">[1](#fn:1)</sup> is detect the `kapt` folder which contains any sources generated from annotation processing. This can be fixed using the IDEA gradle plugin<sup id="fnref:2">[2](#fn:2)</sup>.

The idea plugin isn't able to mark the kapt folder as a source directory unless it's been added to the gradle source set.

```gradle
sourceSets.main.java.srcDir file("${"$"}buildDir/generated/source/kapt/")

```

The next step is to tell the idea plugin to mark the kapt folder as a generated sources root in the module<sup id="fnref:3">[3](#fn:3)</sup>.

```gradle
idea {  
    module {
        // Tell idea to mark the folder as generated sources
        generatedSourceDirs += file("${"$"}buildDir/generated/source/kapt/")
    }
}
```

When put together your build script should look a little like this.

```gradle
apply plugin: 'idea'

...

kapt {  
    generateStubs = true
}

// Add kapt directory to sources
sourceSets.main.java.srcDir file("${"$"}buildDir/generated/source/kapt/")

idea {  
    module {
// Tell idea to mark the folder as generated sources
        generatedSourceDirs += file("${"$"}buildDir/generated/source/kapt/")
    }
}
```

###### Footnotes and references

1. <a name="fn:1"></a> →, V. (2015). Better Annotation Processing: Supporting Stubs in kapt. [online] Kotlin Blog. Available at: [https://blog.jetbrains.com/kotlin/2015/06/better-annotation-processing-supporting-stubs-in-kapt/#comment-36065](https://blog.jetbrains.com/kotlin/2015/06/better-annotation-processing-supporting-stubs-in-kapt/#comment-36065) [Accessed 25 Jun. 2016]. [↩](#fnref:1 "return to article")

2. <a name="fn:2"></a> Docs.gradle.org. (2016). The IDEA Plugin - Gradle User Guide Version 2.14. [online] Available at: [https://docs.gradle.org/current/userguide/idea_plugin.html](https://docs.gradle.org/current/userguide/idea_plugin.html) [Accessed 25 Jun. 2016]. [↩](#fnref:2 "return to article")

3. <a name="fn:3"></a> Docs.gradle.org. (2016). IdeaModule - Gradle DSL Version 2.14. [online] Available at: [https://docs.gradle.org/current/dsl/org.gradle.plugins.ide.idea.model.IdeaModule.html](https://docs.gradle.org/current/dsl/org.gradle.plugins.ide.idea.model.IdeaModule.html) [Accessed 25 Jun. 2016]. [↩](#fnref:3 "return to article")
"""

Article(
  title = "How to get IDEA to detect kotlin generated sources using Gradle",
  url = "https://blog.nishtahir.com/2016/06/25/how-to-get-idea-to-detect-kotlin-generated-sources-using-gradle/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Nish Tahir",
  date = LocalDate.of(2016, 6, 25),
  body = body
)
