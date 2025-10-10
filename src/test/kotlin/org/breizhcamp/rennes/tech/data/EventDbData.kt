package org.breizhcamp.rennes.tech.data

import org.breizhcamp.rennes.tech.infrastructure.db.model.EventDb
import org.breizhcamp.rennes.tech.infrastructure.db.model.PhysicalVenueDb
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.util.UUID

object EventDbData {

    fun physicalVenueDb(
        id: UUID = UUID.randomUUID(),
        name: String = "Hellowork",
        address: String = "2 Rue de la Mabilais",
        city: String = "Rennes",
        country: String = "fr",
        latitude: Double? = null,
        longitude: Double? = null,
    ) = PhysicalVenueDb(
        id = id,
        name = name,
        address = address,
        city = city,
        country = country,
        latitude = latitude,
        longitude = longitude,
    )

    fun eventDb(
        id: UUID = UUID.randomUUID(),
        sha256: String = "existing-sha256",
        title: String = "Python - a kind of magic !",
        description: String = "Un peu de magie pour cette session Python Rennes de rentrée avec 2 interventions :\n\n* **Python et la magie du typage statique**\n\nPar **Florian Strzelecki**",
        start: OffsetDateTime = ZonedDateTime.parse("2025-10-15T18:45:00+02:00[Europe/Paris]").toOffsetDateTime(),
        end: OffsetDateTime? = ZonedDateTime.parse("2025-10-15T20:45:00+02:00[Europe/Paris]").toOffsetDateTime(),
        timezone: String = "Europe/Paris",
        thumbnailUrl: String? = "https://secure.meetupstatic.com/photos/event/8/6/d/0/highres_530494512.jpeg",
        providerId: String? = "311313392",
        groupId: String = GroupData.python.id.id,
        physicalVenue: PhysicalVenueDb? = physicalVenueDb(),
    ) = EventDb(
        id = id,
        sha256 = sha256,
        title = title,
        description = description,
        startDate = start,
        endDate = end,
        timezone = timezone,
        thumbnailUrl = thumbnailUrl,
        providerId = providerId,
        groupId = groupId,
        physicalVenue = physicalVenue,
    )
}
