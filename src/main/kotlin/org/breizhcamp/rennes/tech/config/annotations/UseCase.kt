package org.breizhcamp.rennes.tech.config.annotations

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Service

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Service
annotation class UseCase(
    @get:AliasFor(annotation = Service::class)
    val value: String = "",
)
