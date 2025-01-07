@file:JvmName("Application")

package link.kotlin.scripts

fun main() {
    val generator = createApplicationModule {}
        .awesomeKotlinGenerator

    // Load data
    val articles = generator.getArticles()
    val links = generator.getLinks()

    // Create README.md
    generator.generateReadme(links)

    // Generate resources for site
    generator.generateSiteResources(links, articles)
}
