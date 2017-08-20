
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
The most common approach to measuring how comprehensive your unit tests are is to look at “code coverage”, literally the number of lines of your code that are executed by your unit tests.

While this isn’t a bad approach, it really only tests what code is executed by your tests, it doesn’t directly measure their ability to detect bugs in your code, which is their purpose. You could easily write unit tests that execute every line of code, and yet will pass regardless of what your code actually does.

Mutation testing addresses this problem. In a completely automated way, it deliberately introduce bugs (“mutations”) in your code, and then verifies that the unit tests fail.

There is an excellent tool called [Pitest](http://pitest.org/) for Java and other JVM languages that does exactly this, so I decided to test it on a relatively [simple library](https://github.com/sanity/pairAdjacentViolators) I created recently (related to machine learning), I did my best to unit test it fairly comprehensively.

My library is implemented in [Kotlin](http://kotlinlang.org/) (which runs on the JVM) and which uses Gradle as a build tool. Adding Pitest was super-easy, I expected some headaches because I’m using Kotlin and not Java, but it worked perfectly first time, exactly as advertised. Very impressive. [Here](https://github.com/sanity/pairAdjacentViolators/commit/38abd1edfdb4389fee4a31be867dfd4c29747a36) are the modifications required to my build.gradle file.

Pitest took between 17 and 30 seconds to run, quite fast considering. Pitest appears to be fairly smart about only running the unit tests that matter for any given file, which suggest that it will scale linearly with the size of your codebase.

[Here](https://sanity.github.io/pairAdjacentViolators/pitest-example/) are the results. They look very similar to a normal code coverage report, you can see each of the three main classes in the project, and if you click on each you can see the respective source file, with each line of code colored red or green.

Take line 17 of [PairAdjacentViolators.kt](https://sanity.github.io/pairAdjacentViolators/pitest-example/PairAdjacentViolators.kt.html), if you mouseover the ‘1’ to the left of the code you can see that here it attempted a mutation, specifically changing:

```kotlin
val combinedWeight = weight + other.weight
```

to..

```kotlin
val combinedWeight = weight - other.weight
```

And, as we would hope, this resulted in a “kill”, a unit test failure.

Now let’s look at line 39, which is marked red:

```kotlin
PAVMode.INCREASING -> previous.y >= next.y
```

If you mouseover this you can see that reversing the condition (>= becomes <=) did result in a failure, but simply changing the conditional boundary (from >= to >) did not. This is unsurprising as this is a very subtle change, but impressive that it was caught.

Every instance where a code mutation doesn’t result in a unit test failure prompts an interesting train of thought about why not. Is it because the code is unimportant (like a toString() method only really used for debugging)? Is it because the change is too subtle and the difference in code behavior is unimportant? Or is it something that should really have been tested? This is a very healthy thought process if you really care about the correctness of your code.

If I was working on code where I needed a very high degree of reliability, I think mutation coverage is an excellent way to ensure that your unit tests are effective, and at pointing out areas where they might require further work.

"""

Article(
  title = "Experimenting with “mutation testing” and Kotlin",
  url = "https://medium.com/@s4n1ty/experimenting-with-mutation-testing-and-kotlin-b515d77e85b5#.9ps78uqer",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Ian Clarke",
  date = LocalDate.of(2016, 10, 16),
  body = body
)
