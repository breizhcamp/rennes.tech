package org.breizhcamp.rennes.tech.infrastructure.groups.dto

import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.GroupId
import org.breizhcamp.rennes.tech.domain.entities.GroupProviderType

data class GroupConfig(
    val id: String,
    val name: String,
    val active: Boolean?,
    val providerType: String,
    val providerId: String,
) {
    fun toDomain() = Group(
        id = GroupId(id),
        name = name,
        description = "",
        providerType = providerType.toDomain(),
        providerId = providerId,
    )

    private fun String.toDomain() = when(this) {
        "meetup" -> GroupProviderType.MEETUP
        else -> throw IllegalArgumentException("Unknown provider type: [$this]")
    }
}
