package org.breizhcamp.rennes.tech.application.controllers

import org.breizhcamp.rennes.tech.application.controllers.dto.toDto
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.use_cases.EventList
import org.breizhcamp.rennes.tech.domain.use_cases.GroupList
import org.breizhcamp.rennes.tech.domain.use_cases.UrlMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import java.time.Instant

@Controller
class HomeCtrl(
    private val groupList: GroupList,
    private val eventList: EventList,
    private val urlMapper: UrlMapper,
) {

    @GetMapping
    fun home(model: Model): String {
        val nextEvents = eventList.displayedNext()
        fillModel(model, nextEvents)
        return "index"
    }

    @GetMapping(value = ["/rss.xml"], produces = ["text/xml"])
    fun rss(model: Model): String {
        val nextEvents = eventList.lastMonthAndNextSixMonth().filter { it.startDate.minusWeeks(1).toInstant().isBefore(Instant.now()) }
        fillModel(model, nextEvents)
        return "rss"
    }

    private fun fillModel(model: Model, events: List<Event>) {
        val groups = groupList.list().map { it to it.toDto(urlMapper.mapGroup(it)) }
        val groupsById = groups.associateBy { it.first.id.id }

        val nextEvents = events.map { event ->
            val group = groupsById[event.groupId.id]
                ?: error("Group [${event.groupId.id}] not found for event [${event.id.id}]")
            event.toDto(urlMapper.mapEvent(event, group.first), group.second)
        }

        model["groups"] = groups.map { it.second }
        model["nextEvents"] = nextEvents
    }

}
