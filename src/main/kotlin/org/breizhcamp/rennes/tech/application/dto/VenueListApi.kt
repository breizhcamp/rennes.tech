package org.breizhcamp.rennes.tech.application.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.domain.entities.Venue

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = PhysicalVenueListApi::class, name = "physical"),
)
sealed class VenueListApi

data class PhysicalVenueListApi(
    val name: String,
    val address: String,
    val city: String,
    val country: String,
) : VenueListApi()


fun Venue.toDto(): VenueListApi = when(this) {
    is PhysicalVenue -> PhysicalVenueListApi(
        name = this.name,
        address = this.address,
        city = this.city,
        country = this.country,
    )
}
