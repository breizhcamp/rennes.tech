package org.breizhcamp.rennes.tech.infrastructure

import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.ports.TimePort
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Adapter
class TimeAdapter: TimePort {
    override fun nowInstant(): Instant = Instant.now()
    override fun nowZoned(): ZonedDateTime = ZonedDateTime.now(ZoneOffset.systemDefault())
}
