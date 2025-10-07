package org.breizhcamp.rennes.tech.infrastructure

import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.GroupId
import org.breizhcamp.rennes.tech.domain.entities.GroupProviderType
import org.breizhcamp.rennes.tech.domain.ports.GroupPort

@Adapter
class GroupAdapter: GroupPort {
    override fun list(): List<Group> {
        return listOf(
            Group(
                id = GroupId("breizhjug"),
                name = "BreizhJUG",
                description = "Le BreizhJUG est le groupe d'utilisateurs Java de Rennes.",
                providerType = GroupProviderType.MEETUP,
                providerId = "breizhjug",
            ),
            Group(
                id = GroupId("software-crafters-rennes"),
                name = "Software Crafters Rennes",
                description = "Pour tou·te·s les Crafters ou qui veulent le devenir.",
                providerType = GroupProviderType.MEETUP,
                providerId = "software-craftsmanship-rennes",
            ),
        )
    }
}
