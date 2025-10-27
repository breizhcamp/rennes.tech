package org.breizhcamp.rennes.tech.application.controllers.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.domain.entities.Venue
import java.math.BigDecimal

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
    val latitude: BigDecimal?,
    val longitude: BigDecimal?,
) : VenueListApi() {
    fun display() = listOf(name, address, city).filter { it.isNotBlank() }.joinToString(", ")
    fun search() = listOf(name, address, city, country).filter { it.isNotBlank() }.joinToString(", ")
}

fun Venue.toDto(): VenueListApi = when(this) {
    is PhysicalVenue -> PhysicalVenueListApi(
        name = this.name,
        address = this.address,
        city = this.city,
        country = this.country,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}
