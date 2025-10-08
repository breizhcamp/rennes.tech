package org.breizhcamp.rennes.tech.infrastructure.db.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "physical_venue")
class PhysicalVenueDb(
    @Id
    var id: UUID,

    var name: String,
    var address: String,
    var city: String,
    var country: String,
    var latitude: Double?,
    var longitude: Double?,
)
