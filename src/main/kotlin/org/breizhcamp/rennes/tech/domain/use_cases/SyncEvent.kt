package org.breizhcamp.rennes.tech.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import org.breizhcamp.rennes.tech.config.annotations.UseCase
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.ports.EventPort
import org.breizhcamp.rennes.tech.domain.ports.GroupPort
import org.breizhcamp.rennes.tech.domain.ports.ProviderPort

private val logger = KotlinLogging.logger {}

/**
 * Synchronize events from groups into database
 */
@UseCase
class SyncEvent(
    private val groupPort: GroupPort,
    private val eventPort: EventPort,
    providerPorts: List<ProviderPort>,
) {
    private val providers = providerPorts.associateBy { it.supports() }

    fun sync() {
        logger.info { "Starting event synchronization" }
        val groups = groupPort.list()
        val updateEvents = groups.flatMap { syncGroup(it) }
        logger.info { "Synchronization done, [${groups.size}] groups processed, [${updateEvents.size}] events updated" }
    }

    private fun syncGroup(group: Group): List<Event> {
        TODO()
    }

}