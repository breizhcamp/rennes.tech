package org.breizhcamp.rennes.tech

import org.breizhcamp.rennes.tech.config.BackConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BackConfig::class)
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
