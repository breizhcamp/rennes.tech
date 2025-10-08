package org.breizhcamp.rennes.tech.infrastructure.db.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "event")
class EventDb(
    @Id
    var id: UUID,

    var title: String,
    var description: String,

    var startDate: ZonedDateTime,
    var endDate: ZonedDateTime?,

    var thumbnailUrl: String?,
    val providerId: String?,

    val groupId: String,

    @OneToOne
    var physicalVenue: PhysicalVenueDb?
)
