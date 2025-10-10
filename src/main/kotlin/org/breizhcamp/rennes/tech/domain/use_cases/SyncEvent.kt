package org.breizhcamp.rennes.tech.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import org.breizhcamp.rennes.tech.config.annotations.UseCase
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.events.SyncEndEvt
import org.breizhcamp.rennes.tech.domain.ports.EventPort
import org.breizhcamp.rennes.tech.domain.ports.EvtNotifPort
import org.breizhcamp.rennes.tech.domain.ports.GroupPort
import org.breizhcamp.rennes.tech.domain.ports.ProviderPort
import org.breizhcamp.rennes.tech.domain.ports.TimePort

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
    providerPorts: List<ProviderPort>,
) {
    private val providers = providerPorts.associateBy { it.supports() }

    fun sync() {
        logger.info { "Starting event synchronization" }
        val groups = groupPort.list()
        val providerEvents = groups.flatMap { getGroupEvents(it) }
        val syncRes = eventPort.syncNext(timePort.nowInstant(), providerEvents, groups)
        evtNotifPort.send(SyncEndEvt(syncRes))

        logger.info { "Synchronization done, [${groups.size}] groups processed, " +
                "created [${syncRes.created.size}], updated [${syncRes.updated.size}], deleted [${syncRes.deleted.size}]" }
    }

    private fun getGroupEvents(group: Group): List<Event> = try {
        val provider = providers[group.providerType] ?: throw IllegalStateException("No provider [${group.providerType}] found for group [${group.id}]")
        provider.retrieveEvents(group)
    } catch (e: Exception) {
        throw IllegalStateException("Error while retrieving events for group [${group.id}]", e)
    }

}
