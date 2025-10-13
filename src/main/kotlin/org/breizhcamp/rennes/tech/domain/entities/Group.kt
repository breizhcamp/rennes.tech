package org.breizhcamp.rennes.tech.domain.entities

import java.time.ZoneId

@JvmInline
value class GroupId(val id: String)

data class Group(
    val id: GroupId,
    val name: String,
    val description: String,

    val providerType: GroupProviderType,
    val providerId: String,
    val timezone: ZoneId = ZoneId.of("Europe/Paris"),

    val filter: Filter?,
)
