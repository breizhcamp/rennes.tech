package org.breizhcamp.rennes.tech.application.rest.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.breizhcamp.rennes.tech.domain.entities.Event
import java.time.ZonedDateTime

data class EventApi(
    @field:Schema(description = "Unique identifier as UUID", example = "b7e74180-00d7-4f50-8283-6139419a23bb")
    val id: String,
    @field:Schema(description = "Title of the event", example = "Kotlin 2.0 news")
    val title: String,
    @field:Schema(description = "Markdown description", example = "**Venez** découvrir les nouveautés de Kotlin 2.0\\nÇa va être bien")
    val description: String,

    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime?,

    @field:Schema(description = "URL of the landscape thumbnail", example = "https://www.example.com/thumb.png")
    val thumbnailUrl: String?,
    @field:Schema(description = "URL the event", example = "https://www.example.com/breizhjug/kotlin-2.0")
    val providerUrl: String,

    val venue: VenueApi,
)

fun Event.toApi(providerUrl: String) = EventApi(
    id = id.id.toString(),
    title = title,
    description = description,

    startDate = startDate,
    endDate = endDate,

    thumbnailUrl = thumbnailUrl,
    providerUrl = providerUrl,
    venue = venue.toApi()
)