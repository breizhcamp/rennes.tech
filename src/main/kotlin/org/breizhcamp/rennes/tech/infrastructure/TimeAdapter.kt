package org.breizhcamp.rennes.tech.infrastructure

import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.ports.TimePort
import java.time.Instant

@Adapter
class TimeAdapter: TimePort {
    override fun nowInstant(): Instant = Instant.now()
}
