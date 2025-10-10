package org.breizhcamp.rennes.tech.domain.ports

import java.time.Instant

interface TimePort {

    fun nowInstant(): Instant

}
