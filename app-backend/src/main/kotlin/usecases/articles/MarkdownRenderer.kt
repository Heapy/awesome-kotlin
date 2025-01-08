package usecases.articles

import org.commonmark.parser.Parser
import org.commonmark.renderer.Renderer

class MarkdownRenderer(
    private val parser: Parser,
    private val renderer: Renderer
) {
    fun render(md: String): String {
        val document = parser.parse(md)
        return renderer.render(document)
    }
}
