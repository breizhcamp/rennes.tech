package org.breizhcamp.rennes.tech.domain.ports

import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.GroupProviderType

/**
 * This interface will be implemented for each provider, so several instance of it will exist in the context
 */
interface ProviderPort {

    fun supports(): GroupProviderType
    fun retrieveEvents(group: Group): List<Event>

}