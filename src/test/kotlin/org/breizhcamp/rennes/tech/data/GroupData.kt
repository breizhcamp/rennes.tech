package org.breizhcamp.rennes.tech.data

import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.GroupId
import org.breizhcamp.rennes.tech.domain.entities.GroupProviderType

object GroupData {

    val python = Group(
        id = GroupId("python"),
        name = "Python Rennes",
        description = "Groupe des passionnés de Python à Rennes",
        providerType = GroupProviderType.MEETUP,
        providerId = "python-rennes",
    )

}