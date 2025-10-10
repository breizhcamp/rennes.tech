package org.breizhcamp.rennes.tech.data

import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.EventId
import org.breizhcamp.rennes.tech.domain.entities.GroupId
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

object EventData {

    fun physicalVenue(
        name: String = "Hellowork",
        address: String = "2 Rue de la Mabilais",
        city: String = "Rennes",
        country: String = "fr",
        latitude: Double? = null,
        longitude: Double? = null,
    ) = PhysicalVenue(
        name = name,
        address = address,
        city = city,
        country = country,
        latitude = latitude,
        longitude = longitude,
    )

    fun event(
        id: UUID = UUID.randomUUID(),
        title: String = "Python - a kind of magic !",
        description: String = "Un peu de magie pour cette session Python Rennes de rentrée avec 2 interventions :\n\n* **Python et la magie du typage statique**\n\nPar **Florian Strzelecki**",
        start: ZonedDateTime = ZonedDateTime.parse("2025-10-15T18:45:00+02:00[Europe/Paris]"),
        end: ZonedDateTime? = ZonedDateTime.parse("2025-10-15T20:45:00+02:00[Europe/Paris]"),
        thumbnailUrl: String? = "https://secure.meetupstatic.com/photos/event/8/6/d/0/highres_530494512.jpeg",
        providerId: String? = "311313392",
        groupId: GroupId = GroupData.python.id,
        venue: PhysicalVenue = physicalVenue(),
    ) = Event(
        id = EventId(id),
        title = title,
        description = description,
        startDate = start.withZoneSameInstant(ZoneId.of("Europe/Paris")),
        endDate = end?.withZoneSameInstant(ZoneId.of("Europe/Paris")),
        thumbnailUrl = thumbnailUrl,
        providerId = providerId,
        groupId = groupId,
        venue = venue,
    )
}
