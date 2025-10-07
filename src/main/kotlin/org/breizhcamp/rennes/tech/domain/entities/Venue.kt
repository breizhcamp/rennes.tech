package org.breizhcamp.rennes.tech.domain.entities

sealed class Venue

data class PhysicalVenue(
    val name: String,
    val address: String,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
) : Venue()
