package org.breizhcamp.rennes.tech.domain.ports

import java.time.Instant
import java.time.ZonedDateTime

interface TimePort {

    fun nowInstant(): Instant
    fun nowZoned(): ZonedDateTime

}
