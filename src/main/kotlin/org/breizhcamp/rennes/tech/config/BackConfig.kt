package org.breizhcamp.rennes.tech.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "rennes.tech.back")
data class BackConfig(
    val meetup: MeetupConfig,
)

data class MeetupConfig(
    val baseUrl: String,
)
