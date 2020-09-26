package link.kotlin.scripts

import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.Renderer
import org.commonmark.renderer.html.HtmlRenderer

interface MarkdownRenderer {
    fun render(md: String): String

    companion object
}

private class CommonMarkMarkdownRenderer(
    private val parser: Parser,
    private val renderer: Renderer
) : MarkdownRenderer {
    override fun render(md: String): String {
        val document = parser.parse(md)
        return renderer.render(document)
    }
}

fun MarkdownRenderer.Companion.default(): MarkdownRenderer {
    val extensions = listOf(TablesExtension.create())
    val parser = Parser.builder().extensions(extensions).build()
    val renderer = HtmlRenderer.builder().extensions(extensions).build()

    return CommonMarkMarkdownRenderer(
        parser = parser,
        renderer = renderer
    )
}
