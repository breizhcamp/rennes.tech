package org.breizhcamp.rennes.tech.application.markdown

import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.thymeleaf.context.ITemplateContext
import org.thymeleaf.engine.AttributeName
import org.thymeleaf.model.IProcessableElementTag
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor
import org.thymeleaf.processor.element.IElementTagStructureHandler
import org.thymeleaf.standard.expression.StandardExpressions
import org.thymeleaf.templatemode.TemplateMode


class MarkdownProcessor(dialectPrefix: String): AbstractAttributeTagProcessor(
    TemplateMode.HTML,
    dialectPrefix,
    null,
    false,
    ATTR_NAME,
    true,
    PRECEDENCE,
    true
) {

    companion object {
        val ATTR_NAME = "toHtml"
        val PRECEDENCE = 10000
    }

    override fun doProcess(context: ITemplateContext?, tag: IProcessableElementTag?, attributeName: AttributeName?, attributeValue: String?, structureHandler: IElementTagStructureHandler?) {
        val ctx = context ?: return
        val handler = structureHandler ?: return

        val configuration = ctx.configuration
        val parser = StandardExpressions.getExpressionParser(configuration)
        val expression = parser.parseExpression(ctx, attributeValue)
        val markdownContent = expression.execute(ctx) as String
        val renderedMarkdown: String = renderMarkdown(markdownContent)
        handler.setBody(renderedMarkdown, false)
    }

    private fun renderMarkdown(markdown: String): String {
        val parser = Parser.builder().build()
        val renderer = HtmlRenderer.builder().build()
        return renderer.render(parser.parse(markdown))
    }
}
