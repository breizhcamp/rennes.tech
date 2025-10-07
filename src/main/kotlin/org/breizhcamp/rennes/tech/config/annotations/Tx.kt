package org.breizhcamp.rennes.tech.config.annotations

import org.springframework.transaction.annotation.Transactional
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

@Target(CLASS, FUNCTION)
@Retention(RUNTIME)
@Transactional
annotation class Tx()
