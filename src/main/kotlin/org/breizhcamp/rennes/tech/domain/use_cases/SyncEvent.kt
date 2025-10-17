package org.breizhcamp.rennes.tech.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import org.breizhcamp.rennes.tech.config.annotations.UseCase
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.events.SyncEndEvt
import org.breizhcamp.rennes.tech.domain.ports.*

private val logger = KotlinLogging.logger {}

/**
 * Synchronize events from groups into database
 */
@UseCase
class SyncEvent(
    private val groupPort: GroupPort,
    private val eventPort: EventPort,
    private val timePort: TimePort,
    private val evtNotifPort: EvtNotifPort,
    private val cachePort: CachePort,
    providerPorts: List<ProviderPort>,
) {
    private val providers = providerPorts.associateBy { it.supports() }

    fun sync() {
        logger.info { "Starting event synchronization" }
        val groups = groupPort.list()
        val providerEvents = groups.flatMap { getGroupEvents(it) }
        // we sync take some margin (1 day) when syncing with the min start date
        val startDate = (providerEvents.minOfOrNull { it.startDate } ?: timePort.nowZoned()).minusDays(1)
        val syncRes = eventPort.syncNext(startDate.toInstant(), providerEvents, groups)

        cachePort.invalidate(EventList.EVT_CACHE)
        evtNotifPort.send(SyncEndEvt(syncRes))

        logger.info { "Synchronization done, [${groups.size}] groups processed, " +
                "created [${syncRes.created.size}], updated [${syncRes.updated.size}], deleted [${syncRes.deleted.size}]" }

        syncRes.created.forEach { logger.info { "Event created [${it.groupId.id}] ${it.startDate} - ${it.title}" } }
        syncRes.updated.forEach { logger.info { "Event updated [${it.groupId.id}] ${it.startDate} - ${it.title}" } }
        syncRes.deleted.forEach { logger.info { "Event deleted [${it.groupId.id}] ${it.startDate} - ${it.title}" } }
    }

    private fun getGroupEvents(group: Group): List<Event> = try {
        val provider = providers[group.providerType] ?: throw IllegalStateException("No provider [${group.providerType}] found for group [${group.id}]")
        provider.retrieveEvents(group).filter { it.filter(group) }
    } catch (e: Exception) {
        throw IllegalStateException("Error while retrieving events for group [${group.id}]", e)
    }

    private fun Event.filter(group: Group): Boolean {
        val filter = group.filter ?: return true
        var res = false
        filter.title?.let { res = title.contains(it) }
        return res
    }

}
