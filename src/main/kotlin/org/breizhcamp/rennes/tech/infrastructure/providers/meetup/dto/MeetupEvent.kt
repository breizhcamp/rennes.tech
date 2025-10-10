package org.breizhcamp.rennes.tech.infrastructure.providers.meetup.dto

import java.time.OffsetDateTime

data class MeetupEvent(
    val id: String,
    val title: String,
    val description: String?,

    val venue: MeetupVenue?,

    val dateTime: OffsetDateTime,
    val endTime: OffsetDateTime?,

    val featuredEventPhoto: PhotoInfo?,
)

data class MeetupVenue(
    val name: String,
    val address: String,
    val city: String,
    val country: String,
)

data class PhotoInfo(
    val highResUrl: String?,
)