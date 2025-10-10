package org.breizhcamp.rennes.tech.infrastructure.db.repos

import org.breizhcamp.rennes.tech.infrastructure.db.model.PhysicalVenueDb
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PhysicalVenueRepo: JpaRepository<PhysicalVenueDb, UUID>
