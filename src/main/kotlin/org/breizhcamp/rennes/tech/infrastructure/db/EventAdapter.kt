package org.breizhcamp.rennes.tech.infrastructure.db

import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.config.annotations.Tx
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.GroupProviderType
import org.breizhcamp.rennes.tech.domain.entities.SyncRes
import org.breizhcamp.rennes.tech.domain.ports.EventPort
import org.breizhcamp.rennes.tech.infrastructure.db.mapping.sha256
import org.breizhcamp.rennes.tech.infrastructure.db.mapping.toDb
import org.breizhcamp.rennes.tech.infrastructure.db.mapping.toDomain
import org.breizhcamp.rennes.tech.infrastructure.db.mapping.update
import org.breizhcamp.rennes.tech.infrastructure.db.model.EventDb
import org.breizhcamp.rennes.tech.infrastructure.db.repos.EventRepo
import org.breizhcamp.rennes.tech.infrastructure.db.repos.PhysicalVenueRepo
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset

@Adapter
class EventAdapter(
    private val eventRepo: EventRepo,
    private val physicalVenueRepo: PhysicalVenueRepo,
): EventPort {
    companion object {
        private val DELETE_TIME_MARGIN = Duration.ofMinutes(20)
    }

    override fun listAfter(since: Instant): List<Event> {
        val startDate = since.atOffset(ZoneOffset.UTC)
        return eventRepo.findAllAfter(startDate).map { it.toDomain() }
    }

    @Tx
    override fun syncNext(since: Instant, events: List<Event>, groups: List<Group>): SyncRes {
        val grp = groups.associateBy { it.id.id }
        val startDate = since.atOffset(ZoneOffset.UTC)
        val existing = eventRepo.findAllAfter(startDate)
        val toCreate = events.filter { e -> existing.none { e.sameAs(it, grp) } }.ifEmpty { null }
        val toUpdate = existing.filter { e -> events.any { it.sameAs(e, grp) && it.sha256() != e.sha256 } }
        val toDelete = existing.filter { e -> events.none { it.sameAs(e, grp) } && e.startDate > startDate.plus(DELETE_TIME_MARGIN) }

        val created = toCreate?.map { createEvent(it) } ?: emptyList()
        toUpdate.forEach { e -> updateEvent(e, events.first { it.sameAs(e, grp) }) }
        eventRepo.deleteAll(toDelete)

        return SyncRes(created.map { it.toDomain() }, toUpdate.map { it.toDomain() }, toDelete.map { it.toDomain() })
    }

    private fun createEvent(event: Event): EventDb {
        val db = event.toDb()
        db.physicalVenue?.let { physicalVenueRepo.save(it) }
        return eventRepo.save(db)
    }

    private fun updateEvent(existing: EventDb, newUpdate: Event) {
        existing.update(newUpdate)
        existing.physicalVenue?.let { physicalVenueRepo.save(it) }
        //existing will be saved at the end of the transaction as it already in persistence context
    }

    private fun Event.sameAs(other: EventDb, groups: Map<String, Group>): Boolean =
        groups.provider(this.groupId.id) == groups.provider(other.groupId) && this.providerId == other.providerId

    private fun Map<String, Group>.provider(id: String): GroupProviderType =
        this[id]?.providerType ?: error("Group id [${id}] not found")
}
