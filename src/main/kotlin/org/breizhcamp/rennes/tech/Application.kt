package org.breizhcamp.rennes.tech

import org.breizhcamp.rennes.tech.config.BackConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableConfigurationProperties(BackConfig::class)
@EnableScheduling
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
