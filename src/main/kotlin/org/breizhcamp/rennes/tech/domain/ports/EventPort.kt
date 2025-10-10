package org.breizhcamp.rennes.tech.domain.ports

import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.SyncRes
import java.time.Instant

interface EventPort {

    fun listAfter(since: Instant): List<Event>
    fun syncNext(since: Instant, events: List<Event>, groups: List<Group>): SyncRes
}
