package org.breizhcamp.rennes.tech.application.thymeleaf

import org.springframework.stereotype.Component
import org.thymeleaf.dialect.AbstractProcessorDialect
import org.thymeleaf.processor.IProcessor

@Component
class MarkdownDialect: AbstractProcessorDialect("Markdown Dialect", DIALECT_PREFIX, 1000) {
    companion object {
        const val DIALECT_PREFIX = "markdown"
    }

    override fun getProcessors(dialectPrefix: String?): Set<IProcessor?>? {
        return setOf(MarkdownProcessor(DIALECT_PREFIX))
    }
}
