package org.breizhcamp.rennes.tech.domain.use_cases

import org.breizhcamp.rennes.tech.config.annotations.UseCase
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.ports.EventPort
import org.breizhcamp.rennes.tech.domain.ports.TimePort

@UseCase
class EventList(
    private val eventPort: EventPort,
    private val timePort: TimePort,
) {

    fun next(): List<Event> = eventPort.listAfter(since = timePort.nowInstant())

}
