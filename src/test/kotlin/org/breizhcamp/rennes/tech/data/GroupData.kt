package org.breizhcamp.rennes.tech.data

import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.GroupId
import org.breizhcamp.rennes.tech.domain.entities.GroupProviderType
import java.time.ZoneId

object GroupData {

    val python = Group(
        id = GroupId("python"),
        name = "Python Rennes",
        description = "Groupe des passionnés de Python à Rennes",
        providerType = GroupProviderType.MEETUP,
        providerId = "python-rennes",
        timezone = ZoneId.of("Europe/Paris"),
    )

    fun group(
        id: String = "python",
        name: String = "Python Rennes",
        description: String = "Groupe des passionnés de Python à Rennes",
        providerType: GroupProviderType = GroupProviderType.MEETUP,
        providerId: String = "python-rennes",
        timezone: ZoneId = ZoneId.of("Europe/Paris"),
    ): Group = Group(
        id = GroupId(id),
        name = name,
        description = description,
        providerType = providerType,
        providerId = providerId,
        timezone = timezone,
    )
}
