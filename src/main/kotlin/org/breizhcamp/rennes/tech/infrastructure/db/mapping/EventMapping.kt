package org.breizhcamp.rennes.tech.infrastructure.db.mapping

import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.EventId
import org.breizhcamp.rennes.tech.domain.entities.GroupId
import org.breizhcamp.rennes.tech.domain.entities.UnknownVenue
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.infrastructure.db.model.EventDb
import org.breizhcamp.rennes.tech.infrastructure.db.model.PhysicalVenueDb
import java.time.ZoneId
import java.util.*

fun EventDb.toDomain(): Event {
    val zone = ZoneId.of(timezone)
    return Event(
        id = EventId(id),
        title = title,
        description = description,
        startDate = startDate.atZoneSameInstant(zone),
        endDate = endDate?.atZoneSameInstant(zone),
        thumbnailUrl = thumbnailUrl,
        providerId = providerId,
        groupId = GroupId(groupId),
        venue = physicalVenue?.toDomain() ?: UnknownVenue(),
    )
}

fun EventDb.update(event: Event, physicalVenueDb: PhysicalVenueDb?) {
    title = event.title
    sha256 = event.sha256()
    description = event.description
    startDate = event.startDate.toOffsetDateTime()
    endDate = event.endDate?.toOffsetDateTime()
    timezone = event.startDate.zone.id
    thumbnailUrl = event.thumbnailUrl
    physicalVenue = physicalVenueDb
}

fun Event.toDb() = EventDb(
    id = id.id,
    sha256 = sha256(),
    title = title,
    description = description,
    startDate = startDate.toOffsetDateTime(),
    endDate = endDate?.toOffsetDateTime(),
    timezone = startDate.zone.id,
    thumbnailUrl = thumbnailUrl,
    providerId = providerId,
    groupId = groupId.id,
    physicalVenue = when (venue) {
        is PhysicalVenue -> venue.toDb(UUID.randomUUID())
        is UnknownVenue -> null
    },
)

fun Event.sha256(): String {
    return (listOf(title, description, startDate.toString(), endDate?.toString(), thumbnailUrl) + venue.list())
        .joinToString("\n")
        .hash256()
}

private fun String.hash256(): String {
    val bytes = java.security.MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}
