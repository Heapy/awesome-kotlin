package link.kotlin.scripts.import

import link.kotlin.scripts.utils.writeFile
import org.commonmark.node.BulletList
import org.commonmark.node.Document
import org.commonmark.node.Heading
import org.commonmark.node.Link
import org.commonmark.node.ListItem
import org.commonmark.node.Paragraph
import org.commonmark.node.Text
import org.commonmark.parser.Parser
import java.nio.file.Files
import java.nio.file.Paths

private val communities: String = Files.readString(
    Paths.get("src/main/kotlin/link/kotlin/scripts/import/user-group-list.md")
)

fun convertToAwesomeLink() {
    val node = Parser.builder().build().parse(communities)

    if (node is Document) {
        var child = node.firstChild

        val process = mutableMapOf<Heading, BulletList>()

        while (true) {
            if (child is Heading) {
                val next = child.next
                if (next is BulletList) {
                    process[child] = next
                }
            }

            child = child.next ?: break
        }

        val categoryBuilder = StringBuilder()

        categoryBuilder.append("""
            // Don't edit manually, check Communities.kt
            category("Kotlin User Groups") {
        """.trimIndent())
        categoryBuilder.append("\n")

        process.forEach {
            val text = it.key.firstChild
            val subcategory = (text as Text).literal

            val subcategoryBuilder = buildString {
                append("""  subcategory("$subcategory") {""")
                append("\n")

                var item = it.value.firstChild
                while (true) {
                    val p = ((item as ListItem).firstChild as Paragraph)
                    val a = (p.firstChild as Link)
                    val ah = a.destination
                    val at = (a.firstChild as Text).literal.replace("\"", "\\\"")
                    val t = (p.lastChild as Text).literal.removePrefix(", ")
                    val link = """
                   |    link {
                   |      kug = "$at"
                   |      desc = "$t"
                   |      href = "$ah"
                   |      setTags("$t")
                   |    }
                   |
                """.trimMargin()

                    append(link)
                    item = item.next ?: break
                }
                append("  }\n")
            }

            categoryBuilder.append(subcategoryBuilder)
        }

        categoryBuilder.append("}")
        categoryBuilder.append("\n")

        writeFile("./src/main/resources/links/UserGroups.awesome.kts", categoryBuilder.toString())
    }

    println("Done")
}

fun main() {
    convertToAwesomeLink()
}
