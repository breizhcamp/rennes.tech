package org.breizhcamp.rennes.tech.infrastructure.db

import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.EventId
import org.breizhcamp.rennes.tech.domain.entities.GroupId
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.domain.ports.EventPort
import java.time.ZonedDateTime
import java.util.UUID

@Adapter
class EventAdapter: EventPort {
    override fun list(since: ZonedDateTime?): List<Event> {
        val venue = PhysicalVenue(
            name = "ISTIC",
            address = "Campus de Beaulieu, 263 Avenue du Général Leclerc",
            city = "Rennes",
            country = "France",
            latitude = 48.1147,
            longitude = -1.6743
        )

        return listOf(Event(
            id = EventId(UUID.randomUUID()),
            title = "Kotlin",
            description = "Kotlin is awesome",
            startDate = ZonedDateTime.now(),
            endDate = ZonedDateTime.now().plusHours(1),
            thumbnailUrl = null,
            providerId = "1234568",
            groupId = GroupId("breizhjug"),
            venue = venue
        ))
    }
}
