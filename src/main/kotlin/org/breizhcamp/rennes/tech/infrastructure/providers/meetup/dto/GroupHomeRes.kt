package org.breizhcamp.rennes.tech.infrastructure.providers.meetup.dto

data class GroupHomeRes(
    val data: GroupHomeData,
)

data class GroupHomeData(
    val groupByUrlname: GroupByUrlname,
)

data class GroupByUrlname(
    val upcomingEvents: GqlList<MeetupEvent>,
)

data class GqlList<T>(
    val edges: List<GqlEdge<T>>?,
)

data class GqlEdge<T>(
    val node: T,
)