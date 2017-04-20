package link.kotlin.scripts

interface ReadmeGenerator {
    fun generate(): String
}

class DefaultReadmeGenerator(
    private val projects: List<Category>,
    private val articles: List<Category>
) : ReadmeGenerator {
    override fun generate(): String {
        return generate(projects + articles)
    }
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
    """### ${getAnchor(namespace + "-" + name)}$name <sup>${link("Back ⇈", "$namespace-$name-subcategory")}</sup>"""

internal fun getTocCategoryName(name: String) =
    """### ${getAnchor("$name-category")}${link(name, name)}"""

internal fun getTocSubcategoryName(name: String, namespace: String) =
    """* ${getAnchor("$namespace-$name-subcategory")}${link(name, "$namespace-$name")}"""

internal fun tableOfContent(links: Links): String {
    fun getSubcategories(category: Category): String {
        return category
            .subcategories
            .map { (_, name) ->
                getTocSubcategoryName(name, category.name)
            }
            .joinToString("\n")
    }

    return links
        .map { category ->
            "${getTocCategoryName(category.name)}\n${getSubcategories(category)}"
        }
        .joinToString("\n\n")
}

internal fun getLinks(links: Links): String {
    return links.map { category ->
        val subcategories = category.subcategories.map { subcategory ->
            val mdLinks = subcategory.links.map { link ->
                val getDesc = fun(desc: String?) = if (desc.isNullOrEmpty()) "" else "- $desc"

                "* [${link.name}](${link.href}) ${getDesc(link.desc)}"
            }.joinToString("\n")

            "${getSubcategoryName(subcategory.name, category.name)}\n$mdLinks\n"
        }.joinToString("\n")

        "${getCategoryName(category.name)}\n${subcategories}\n"
    }.joinToString("\n")
}

internal fun generate(links: Links): String {
    val template = """# Awesome Kotlin ([https://kotlin.link](https://kotlin.link))

A curated list of awesome Kotlin related stuff inspired by awesome-java. :octocat:

[![List of Awesome List Badge](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) [![Travis CI Build Status Badge](https://api.travis-ci.org/KotlinBy/awesome-kotlin.svg?branch=master)](https://travis-ci.org/KotlinBy/awesome-kotlin) [![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

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

    return template
}
