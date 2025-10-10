package org.breizhcamp.rennes.tech.infrastructure.db.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "event")
class EventDb(
    @Id
    var id: UUID,
    var sha256: String,

    var title: String,
    var description: String,

    var startDate: OffsetDateTime,
    var endDate: OffsetDateTime?,
    var timezone: String,

    var thumbnailUrl: String?,
    val providerId: String?,

    val groupId: String,

    @OneToOne
    var physicalVenue: PhysicalVenueDb?
)
