package org.breizhcamp.rennes.tech.application.rest.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.swagger.v3.oas.annotations.media.Schema
import org.breizhcamp.rennes.tech.domain.entities.UnknownVenue
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.domain.entities.Venue
import java.math.BigDecimal

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = PhysicalVenueApi::class, name = "physical"),
    JsonSubTypes.Type(value = UnknownVenueApi::class, name = "unknown"),
)
@Schema(description = "Base class of an event venue")
sealed class VenueApi

@Schema(description = "Define an unknown venue for this event, type: unknown")
class UnknownVenueApi : VenueApi()

@Schema(description = "Location of the event if it's a physical event, type: physical")
data class PhysicalVenueApi(
    @field:Schema(example = "ISTIC")
    val name: String,
    @field:Schema(example = "263 Av. Général Leclerc Bâtiment 12 DC")
    val address: String,
    @field:Schema(example = "Rennes")
    val city: String,
    @field:Schema(example = "FR")
    val country: String,
    val latitude: BigDecimal?,
    val longitude: BigDecimal?,
): VenueApi()

fun Venue.toApi(): VenueApi = when(this) {
    is PhysicalVenue -> PhysicalVenueApi(
        name = name,
        address = address,
        city = city,
        country = country,
        latitude = latitude,
        longitude = longitude
    )
    is UnknownVenue -> UnknownVenueApi()
}