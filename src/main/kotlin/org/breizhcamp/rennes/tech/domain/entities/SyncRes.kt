package org.breizhcamp.rennes.tech.domain.entities

data class SyncRes(
    val created: List<Event>,
    val updated: List<Event>,
    val deleted: List<Event>,
)
