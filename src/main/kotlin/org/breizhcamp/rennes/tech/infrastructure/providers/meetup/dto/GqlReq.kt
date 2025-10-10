package org.breizhcamp.rennes.tech.infrastructure.providers.meetup.dto

data class GqlReq(
    val operationName: String,
    val variables: Map<String, Any>,
    val extensions: GqlReqExtensions?,
)

data class GqlReqExtensions(
    val persistedQuery: PersistedQuery,
)

data class PersistedQuery(
    val sha256Hash: String,
    val version: Int = 1,
)
