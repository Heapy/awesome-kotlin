//package link.kotlin.backend.services
//
//import link.kotlin.common.Link
//import link.kotlin.legacy.Category
//import link.kotlin.legacy.toCategories
//
//interface ReadmeGenerator {
//    fun generate(links: List<Link>): String
//}
//
//class DefaultReadmeGenerator : ReadmeGenerator {
//    override fun generate(links: List<Link>): String {
//        return template(links.toCategories())
//    }
//}
//
//internal fun getLinks(links: List<Category>): String {
//    return links.joinToString("\n") { category ->
//        val subcategories = category.subcategories.joinToString("\n") { subcategory ->
//            val mdLinks = subcategory.links.joinToString("\n") { link ->
//                val getDesc = fun(desc: String?) = if (desc.isNullOrEmpty()) "" else "- $desc"
//
//                "* [${link.name}](${link.href}) ${getDesc(link.desc)}"
//            }
//
//            "### ${subcategory.name}\n $mdLinks \n"
//        }
//
//        "## ${category.name}\n $subcategories \n"
//    }
//}
//
//internal fun template(links: List<Category>): String {
//    val template = """<!--
//    This is GENERATED file
//    YOU SHOULD NEVER EDIT IT MANUALLY
//
//    Please read CONTRIBUTING.md for more information.
//-->
//
//# Awesome Kotlin ([https://kotlin.link](https://kotlin.link))
//
//A curated list of awesome Kotlin related stuff inspired by awesome-java. :octocat:
//
//[![List of Awesome List Badge](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) [![Travis CI Build Status Badge](https://api.travis-ci.org/KotlinBy/awesome-kotlin.svg?branch=master)](https://travis-ci.org/KotlinBy/awesome-kotlin) [![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
//
//:newspaper: [RSS Feed of articles, videos, slides, updates (20 latest articles)](http://kotlin.link/rss.xml)
//
//:newspaper: [RSS Feed of articles, videos, slides, updates (full archive)](http://kotlin.link/rss-full.xml)
//
//## Spread Awesome Kotlin!
//
//Here awesome badge for your project:
//
//```markdown
//[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
//```
//
//${getLinks(links)}
//
//---
//
//[![CC0](https://licensebuttons.net/p/zero/1.0/80x15.png)](http://creativecommons.org/publicdomain/zero/1.0/)
//"""
//
//    return template
//}
