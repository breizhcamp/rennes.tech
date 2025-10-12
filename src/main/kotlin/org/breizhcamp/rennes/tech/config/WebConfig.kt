package org.breizhcamp.rennes.tech.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode

@Configuration
class WebConfig {

    @Autowired
    lateinit var templateEngine: SpringTemplateEngine

    @Autowired
    lateinit var appContext: ApplicationContext


    @PostConstruct
    fun addXmlResolver() {
        val xmlResolver = SpringResourceTemplateResolver()
        xmlResolver.setApplicationContext(appContext)
        xmlResolver.order = 1
        xmlResolver.prefix = "classpath:/templates/"
        xmlResolver.suffix = ".xml"
        xmlResolver.templateMode = TemplateMode.XML
        xmlResolver.characterEncoding = "UTF-8"
        xmlResolver.checkExistence = true
        templateEngine.addTemplateResolver(xmlResolver)
    }
}
