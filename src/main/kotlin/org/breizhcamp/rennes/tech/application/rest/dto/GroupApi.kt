package org.breizhcamp.rennes.tech.application.rest.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.breizhcamp.rennes.tech.domain.entities.Group

data class GroupApi(
    @field:Schema(description = "Unique identifier", example = "breizhjug")
    val id: String,
    @field:Schema(description = "Name of the group", example = "BreizhJUG")
    val name: String,
    @field:Schema(description = "URL where to find the group events", example = "https://www.meetup.com/fr-FR/breizhjug/")
    val url: String,
)

fun Group.toApi(url: String) = GroupApi(
    id = id.id,
    name = name,
    url = url,
)
