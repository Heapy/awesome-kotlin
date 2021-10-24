package link.kotlin.scripts

import link.kotlin.scripts.dsl.Category

interface ReadmeGenerator {
    fun generate(links: List<Category>): String

    companion object
}

private class MarkdownReadmeGenerator : ReadmeGenerator {
    override fun generate(links: List<Category>): String {
        val readmeLinks = links.filterNot { it.name == "Kotlin User Groups" }
        return generateReadme(readmeLinks)
    }
}

fun ReadmeGenerator.Companion.default(): ReadmeGenerator {
    return MarkdownReadmeGenerator()
}

internal fun normalizeName(name: String): String {
    return name
        .lowercase()
        .replace(Regex("[ ,/\\\\]"), "-") // Replace special symbols with dash
        .replace(Regex("-+"), "-") // Replace multiple dashes with one dash
}

internal fun getAnchor(name: String) =
    """<a name="${normalizeName(name)}"></a>"""

internal fun link(name: String, url: String) =
    """[$name](#${normalizeName(url)})"""

internal fun getCategoryName(name: String) =
    """## ${getAnchor(name)}$name <sup>${link("Back ⇈", "$name-category")}</sup>"""

internal fun getSubcategoryName(name: String, namespace: String) =
    """### ${getAnchor("$namespace-$name")}$name <sup>${link("Back ⇈", "$namespace-$name-subcategory")}</sup>"""

internal fun getTocCategoryName(name: String) =
    """### ${getAnchor("$name-category")}${link(name, name)}"""

internal fun getTocSubcategoryName(name: String, namespace: String) =
    """* ${getAnchor("$namespace-$name-subcategory")}${link(name, "$namespace-$name")}"""

internal fun tableOfContent(links: List<Category>): String {
    fun getSubcategories(category: Category): String {
        return category
            .subcategories.joinToString("\n") { subcategory ->
                getTocSubcategoryName(subcategory.name, category.name)
            }
    }

    return links.joinToString("\n\n") { category ->
        "${getTocCategoryName(category.name)}\n${getSubcategories(category)}"
    }
}

internal fun getLinks(links: List<Category>): String {
    return links.joinToString("\n") { category ->
        val subcategories = category.subcategories.joinToString("\n") { subcategory ->
            val mdLinks = subcategory.links
                .filter { link -> !link.archived }
                .sortedBy { link -> link.star }
                .joinToString("\n") { link ->
                    val getDesc = fun(desc: String?) = if (desc.isNullOrEmpty()) {
                        ""
                    } else {
                        // Hot fix to remove paragraph added by commonmark
                        "- ${desc.removePrefix("<p>").removeSuffix("</p>\n")}"
                    }

                    "* [${link.name}](${link.href}) ${getDesc(link.desc)}"
                }

            "${getSubcategoryName(subcategory.name, category.name)}\n$mdLinks\n"
        }

        "${getCategoryName(category.name)}\n${subcategories}\n"
    }
}

internal fun generateReadme(links: List<Category>): String {
    return """
<!--





    This is GENERATED file, please consult 
    https://github.com/KotlinBy/awesome-kotlin/blob/main/.github/contributing.md
    for instructions.





-->

# Awesome Kotlin ([https://kotlin.link](https://kotlin.link))

A curated list of awesome Kotlin related stuff inspired by awesome-java. :octocat:

Discuss this project in [Kotlin Slack](https://slack.kotlinlang.org/), channel: **# awesome-kotlin**

[![List of Awesome List Badge](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) [![Build](https://github.com/KotlinBy/awesome-kotlin/actions/workflows/build.yml/badge.svg)](https://github.com/KotlinBy/awesome-kotlin/actions/workflows/build.yml) [![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

:newspaper: [RSS Feed of articles, videos, slides, updates (20 latest articles)](https://kotlin.link/rss.xml)

:newspaper: [RSS Feed of articles, videos, slides, updates (full archive)](https://kotlin.link/rss-full.xml)

## Spread Awesome Kotlin!

Here is the awesome badge for your project:

```markdown
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
```

## Table of Contents

${tableOfContent(links)}

${getLinks(links)}

---

[![CC0](https://licensebuttons.net/p/zero/1.0/88x31.png)](https://creativecommons.org/publicdomain/zero/1.0/)
"""
}
