package org.breizhcamp.rennes.tech.domain.use_cases

import org.breizhcamp.rennes.tech.config.annotations.UseCase
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.GroupProviderType

@UseCase
class UrlMapper {

    fun mapGroup(group: Group): String = when (group.providerType) {
        GroupProviderType.MEETUP -> "https://www.meetup.com/fr-FR/${group.providerId}/"
    }

    fun mapEvent(event: Event, group: Group): String = when (group.providerType) {
        GroupProviderType.MEETUP -> "https://www.meetup.com/fr-FR/${group.providerId}/events/${event.providerId}/"
    }
}
