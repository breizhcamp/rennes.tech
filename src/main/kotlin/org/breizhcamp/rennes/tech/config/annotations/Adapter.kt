package org.breizhcamp.rennes.tech.config.annotations

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class Adapter(
    @get:AliasFor(annotation = Component::class)
    val value: String = "",
)
