package link.kotlin.scripts

interface ReadmeGenerator {
    fun generate(links: List<Category>): String

    companion object
}

private class MardownReadmeGenerator : ReadmeGenerator {
    override fun generate(links: List<Category>): String {
        return generateReadme(links)
    }
}

fun ReadmeGenerator.Companion.default(): ReadmeGenerator {
    return MardownReadmeGenerator()
}

internal fun normalizeName(name: String): String {
    return name
        .toLowerCase()
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
            .subcategories.joinToString("\n") { (_, name) ->
                getTocSubcategoryName(name, category.name)
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
                    val getDesc = fun(desc: String?) = if (desc.isNullOrEmpty()) "" else "- $desc"

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
    https://github.com/KotlinBy/awesome-kotlin/blob/legacy/CONTRIBUTING.md
    for instructions.
-->

# Awesome Kotlin ([https://kotlin.link](https://kotlin.link))

A curated list of awesome Kotlin related stuff inspired by awesome-java. :octocat:

Discuss this project in [Kotlin Slack](http://slack.kotlinlang.org/), channel: **# awesome-kotlin**

[![List of Awesome List Badge](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) [![Build Status](https://travis-ci.com/KotlinBy/awesome-kotlin.svg?branch=legacy)](https://travis-ci.com/KotlinBy/awesome-kotlin) [![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

:newspaper: [RSS Feed of articles, videos, slides, updates (20 latest articles)](http://kotlin.link/rss.xml)

:newspaper: [RSS Feed of articles, videos, slides, updates (full archive)](http://kotlin.link/rss-full.xml)

## Spread Awesome Kotlin!

Here awesome badge for your project:

```markdown
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
```

## Table of Contents

${tableOfContent(links)}

${getLinks(links)}

---

[![CC0](https://licensebuttons.net/p/zero/1.0/80x15.png)](http://creativecommons.org/publicdomain/zero/1.0/)
"""
}
