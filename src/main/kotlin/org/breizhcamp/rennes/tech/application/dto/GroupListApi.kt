package org.breizhcamp.rennes.tech.application.dto

import org.breizhcamp.rennes.tech.domain.entities.Group

data class GroupListApi(
    val id: String,
    val name: String,
    val url: String,
)

fun Group.toDto(url: String) = GroupListApi(
    id = this.id.id,
    name = this.name,
    url = url,
)
