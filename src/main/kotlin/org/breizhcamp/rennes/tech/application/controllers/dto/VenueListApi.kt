package org.breizhcamp.rennes.tech.application.controllers.dto

import org.breizhcamp.rennes.tech.domain.entities.UnknownVenue
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.domain.entities.Venue
import java.math.BigDecimal

enum class VenueSearchType { GEO, ADDRESS, NONE }

sealed class VenueListApi {
    abstract fun display(): String
    open fun searchType(): VenueSearchType = VenueSearchType.NONE
    open fun search(): String? = null
}

class UnknownVenueApi : VenueListApi() {
    override fun display() = "Lieu non spécifié pour l'instant"
}

data class PhysicalVenueListApi(
    val name: String,
    val address: String,
    val city: String,
    val country: String,
    val latitude: BigDecimal?,
    val longitude: BigDecimal?,
) : VenueListApi() {
    override fun display() = listOf(name, address, city).filter { it.isNotBlank() }.joinToString(", ")
    override fun searchType() = if (latitude != null && longitude != null) VenueSearchType.GEO else VenueSearchType.ADDRESS
    override fun search() = listOf(name, address, city, country).filter { it.isNotBlank() }.joinToString(", ")
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

    is UnknownVenue -> UnknownVenueApi()
}
