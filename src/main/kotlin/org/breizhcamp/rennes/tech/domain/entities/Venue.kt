package org.breizhcamp.rennes.tech.domain.entities

import java.math.BigDecimal

sealed class Venue

data class PhysicalVenue(
    val name: String,
    val address: String,
    val city: String,
    val country: String,
    val latitude: BigDecimal?,
    val longitude: BigDecimal?,
) : Venue() {
    val completeAddress: String
        get() = listOf(name, address, city, country).filter { it.isNotBlank() }.joinToString(", ")
}
