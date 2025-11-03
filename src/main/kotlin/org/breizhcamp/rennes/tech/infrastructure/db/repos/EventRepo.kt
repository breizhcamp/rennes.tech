package org.breizhcamp.rennes.tech.infrastructure.db.repos

import org.breizhcamp.rennes.tech.infrastructure.db.model.EventDb
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.*

@Repository
interface EventRepo: JpaRepository<EventDb, UUID> {

    @Query("SELECT e FROM EventDb e LEFT JOIN FETCH e.physicalVenue WHERE e.startDate > :startDate ORDER BY e.startDate ASC")
    fun findAllAfter(startDate: OffsetDateTime): List<EventDb>

}
