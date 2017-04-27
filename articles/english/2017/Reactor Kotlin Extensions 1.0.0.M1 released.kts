

import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
I am excited to announce the release of the first milestone of [Reactor Kotlin Extensions](https://github.com/reactor/reactor-kotlin-extensions), which provides Kotlin extensions for Reactor API.

It provides support for Kotlin types like `KClass`, takes advantage of Kotlin [reified type parameters](http://kotlinlang.org/docs/reference/inline-functions.html#reified-type-parameters) and provide various extensions to allow more expressive code. You can see bellow a quick comparaison of Reactor with Java versus Reactor with Kotlin + extensions.

| Java                                         | Kotlin with extensions                       |
|----------------------------------------------|----------------------------------------------|
| `Mono.just("foo")`                           | `"foo".toMono()`                             |
| `Flux.fromIterable(list)`                    | `list.toFlux()`                              |
| `Mono.error(new RuntimeException())`         | `RuntimeException().toMono()`                |
| `Flux.error(new RuntimeException())`         | `RuntimeException().toFlux()`                |
| `flux.ofType(Foo.class)`                     | `flux.ofType()` or `flux.ofType(Foo::class)` |
| `StepVerifier.create(flux).verifyComplete()` | `flux.test().verifyComplete()`               |

To use it in your project, add `https://repo.spring.io/milestone` repository and `io.projectreactor:reactor-kotlin-extensions:1.0.0.M1` dependency.

This is the very first milestone, so feel free to create issues and submit pull requests on [reactor-kotlin-extensions](https://github.com/reactor/reactor-kotlin-extensions) GitHub project.
"""

Article(
  title = "Reactor Kotlin Extensions 1.0.0.M1 released",
  url = "https://spring.io/blog/2017/03/28/reactor-kotlin-extensions-1-0-0-m1-released",
  categories = listOf(
    "Kotlin",
    "Spring"
  ),
  type = article,
  lang = EN,
  author = "Sébastien Deleuze",
  date = LocalDate.of(2017, 3, 28),
  body = body
)
