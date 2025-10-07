package org.breizhcamp.rennes.tech.domain.entities

import java.time.ZonedDateTime
import java.util.*

@JvmInline
value class EventId(val id: UUID)

data class Event(
    val id: EventId,
    val title: String,
    val description: String,

    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime,

    val thumbnailUrl: String?,
    val providerId: String?,

    val groupId: GroupId,
    val venue: Venue,
)
