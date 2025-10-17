package org.breizhcamp.rennes.tech.application.controllers.dto

import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.EventId
import java.time.ZonedDateTime

data class EventListApi(
    val id: EventId,
    val title: String,
    val description: String,

    val url: String,
    val thumbnailUrl: String?,

    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime?,

    val group: GroupListApi,
    val venue: VenueListApi,
)

fun Event.toDto(url: String, group: GroupListApi) = EventListApi(
    id = this.id,
    title = this.title,
    description = this.description,
    url = url,
    thumbnailUrl = this.thumbnailUrl,
    startDate = this.startDate,
    endDate = this.endDate,
    group = group,
    venue = this.venue.toDto(),
)
