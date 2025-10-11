package org.breizhcamp.rennes.tech.application.markdown

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.BulletList
import org.commonmark.node.Code
import org.commonmark.node.Emphasis
import org.commonmark.node.HardLineBreak
import org.commonmark.node.Heading
import org.commonmark.node.ListItem
import org.commonmark.node.Paragraph
import org.commonmark.node.SoftLineBreak
import org.commonmark.node.StrongEmphasis
import org.commonmark.node.Text
import org.commonmark.parser.Parser
import org.springframework.stereotype.Component

@Component
class MarkdownToPlain {

    fun convert(markdown: String): String {
        val document = Parser.builder().build().parse(markdown)
        val sb = StringBuilder()

        document.accept(object : AbstractVisitor() {
            var listDepth = 0
            override fun visit(paragraph: Paragraph) {
                val before = sb.length
                visitChildren(paragraph)
                if (sb.length > before) sb.append("\n\n")
            }
            override fun visit(heading: Heading) {
                visitChildren(heading); sb.append("\n\n")
            }
            override fun visit(text: Text) { sb.append(text.literal) }
            override fun visit(softLineBreak: SoftLineBreak) { sb.append(' ') }
            override fun visit(hardLineBreak: HardLineBreak) { sb.append("\\n") }
            override fun visit(emphasis: Emphasis) { visitChildren(emphasis) }
            override fun visit(strongEmphasis: StrongEmphasis) { visitChildren(strongEmphasis) }
            override fun visit(code: Code) { sb.append(code.literal) }
            override fun visit(bulletList: BulletList) {
                listDepth++; visitChildren(bulletList); listDepth--; sb.append("\n")
            }
            override fun visit(listItem: ListItem) {
                repeat(listDepth) { if (it == listDepth - 1) sb.append("- ") }
                visitChildren(listItem); sb.append("\n")
            }
        })

        return sb.toString()
            .replace(Regex("[ \\t]+"), " ")
            .replace(Regex("\\n{3,}"), "\n\n")
            .trim()
    }

}
