package org.breizhcamp.rennes.tech.infrastructure.db

import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.EventId
import org.breizhcamp.rennes.tech.domain.entities.GroupId
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.domain.ports.EventPort
import org.breizhcamp.rennes.tech.infrastructure.db.model.EventDb
import org.breizhcamp.rennes.tech.infrastructure.db.model.PhysicalVenueDb
import org.breizhcamp.rennes.tech.infrastructure.db.repos.EventRepo
import java.time.ZoneId
import java.time.ZonedDateTime

@Adapter
class EventAdapter(
    private val eventRepo: EventRepo,
): EventPort {
    override fun list(since: ZonedDateTime?): List<Event> {
        val startDate = since ?: ZonedDateTime.now(ZoneId.of("Europe/Paris"))
        return eventRepo.findAllAfter(startDate).map { it.toDomain() }
    }

    private fun EventDb.toDomain() = Event(
        id = EventId(id),
        title = title,
        description = description,
        startDate = startDate,
        endDate = endDate,
        thumbnailUrl = thumbnailUrl,
        providerId = providerId,
        groupId = GroupId(groupId),
        venue = requireNotNull(physicalVenue?.toDomain()),
    )

    private fun PhysicalVenueDb.toDomain() = PhysicalVenue(
        name = name,
        address = address,
        city = city,
        country = country,
        latitude = latitude,
        longitude = longitude,
    )
}
