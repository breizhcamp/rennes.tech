package org.breizhcamp.rennes.tech.infrastructure.db.mapping

import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.domain.entities.Venue
import org.breizhcamp.rennes.tech.infrastructure.db.model.PhysicalVenueDb
import java.util.UUID

fun PhysicalVenueDb.toDomain() = PhysicalVenue(
    name = name,
    address = address,
    city = city,
    country = country,
    latitude = latitude,
    longitude = longitude,
)

fun Venue.toDb(id: UUID) = when (this) {
    is PhysicalVenue -> PhysicalVenueDb(
        id = id,
        name = name,
        address = address,
        city = city,
        country = country,
        latitude = latitude,
        longitude = longitude,
    )
}

fun Venue.list(): List<String> {
    return when (this) {
        is PhysicalVenue -> listOf(name, address, city, country, latitude.toString(), longitude.toString())
    }
}
