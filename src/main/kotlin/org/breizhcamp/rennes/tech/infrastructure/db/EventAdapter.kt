package org.breizhcamp.rennes.tech.infrastructure.db

import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.EventId
import org.breizhcamp.rennes.tech.domain.entities.GroupId
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.domain.ports.EventPort
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

@Adapter
class EventAdapter: EventPort {
    override fun list(since: ZonedDateTime?): List<Event> {
        val venue = PhysicalVenue(
            name = "Epitech",
            address = "12 Rue Jean-Louis Bertrand",
            city = "Rennes",
            country = "France",
            latitude = 48.124645,
            longitude = -1.696879,
        )

        return listOf(Event(
            id = EventId(UUID.randomUUID()),
            title = "Global Day Of Code Retreat",
            description = "Des développeuses et développeurs du monde entier qui se réunissent le même jour pour résoudre le même problème : c'est le Global Day Of Code Retreat",
            startDate = ZonedDateTime.of(2025, 10, 14, 18, 30, 0, 0, ZoneId.of("Europe/Paris")),
            endDate = ZonedDateTime.of(2025, 10, 14, 23, 0, 0, 0, ZoneId.of("Europe/Paris")),
            thumbnailUrl = "https://secure.meetupstatic.com/photos/event/3/8/3/2/highres_530414386.webp?w=640",
            providerId = "311251598",
            groupId = GroupId("software-crafters"),
            venue = venue,
        ))
    }
}
