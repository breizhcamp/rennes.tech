package org.breizhcamp.rennes.tech.infrastructure.groups.dto

import org.breizhcamp.rennes.tech.domain.entities.Filter

data class GroupFilter(
    val title: String?
) {
    fun toDomain() = Filter(title = title)
}
