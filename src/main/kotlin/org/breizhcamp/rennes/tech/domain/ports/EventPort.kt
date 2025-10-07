package org.breizhcamp.rennes.tech.domain.ports

import org.breizhcamp.rennes.tech.domain.entities.Event
import java.time.ZonedDateTime

interface EventPort {

    fun list(since: ZonedDateTime?): List<Event>

}
