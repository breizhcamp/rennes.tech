package org.breizhcamp.rennes.tech.application.rest

import org.breizhcamp.rennes.tech.application.rest.dto.EventApi
import org.breizhcamp.rennes.tech.application.rest.dto.toApi
import org.breizhcamp.rennes.tech.domain.use_cases.EventList
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.use_cases.GroupList
import org.breizhcamp.rennes.tech.domain.use_cases.UrlMapper

@RestController
@RequestMapping("/api/v1/events", produces = ["application/json"])
@Tag(name = "events", description = "Event related operations")
class EventApiCtrl(
    private val eventList: EventList,
    private val groupList: GroupList,
    private val urlMapper: UrlMapper,
) {

    @Operation(summary = "List events", description = "Events for the last month and next six months")
    @GetMapping
    fun list(): List<EventApi> {
        return eventList.lastMonthAndNextSixMonth().toApi()
    }

    @Operation(summary = "Next events", description = "Events displayed as next upcoming")
    @GetMapping("/next")
    fun next(): List<EventApi> =
        eventList.displayedNext().toApi()


    private fun List<Event>.toApi(): List<EventApi> {
        val groups = groupList.list().associateBy { it.id }
        return map {
            val grp = requireNotNull(groups[it.groupId]) { "Group [${it.groupId}] not found when loading events API" }
            it.toApi(urlMapper.mapEvent(it, grp))
        }
    }
}
