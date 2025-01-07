package usecases.sitemap

class SitemapUrl(val url: String)

class Sitemap {
    private val builder = StringBuilder()

    init {
        builder.append("""
        <?xml version="1.0" encoding="UTF-8"?>
        <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
        """.trimIndent())
    }

    operator fun SitemapUrl.unaryPlus() {
        val url = """
        <url>
          <loc>${this.url}</loc>
        </url>
        """.trimIndent()

        builder.append(url)
    }

    fun getSiteMap(): String {
        builder.append("</urlset>")
        return builder.toString()
    }
}

fun sitemap(callback: Sitemap.() -> Unit): String {
    val sm = Sitemap()
    callback(sm)
    return sm.getSiteMap()
}
