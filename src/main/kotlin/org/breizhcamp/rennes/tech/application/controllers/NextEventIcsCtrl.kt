package org.breizhcamp.rennes.tech.application.controllers

import net.fortuna.ical4j.data.CalendarOutputter
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.*
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion
import org.breizhcamp.rennes.tech.application.markdown.MarkdownToPlain
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.entities.GroupId
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.domain.use_cases.EventList
import org.breizhcamp.rennes.tech.domain.use_cases.GroupList
import org.breizhcamp.rennes.tech.domain.use_cases.UrlMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayOutputStream
import java.math.BigDecimal
import java.net.URI

@RestController
class NextEventIcsCtrl(
    private val eventList: EventList,
    private val groupList: GroupList,
    private val urlMapper: UrlMapper,
    private val markdownToPlain: MarkdownToPlain,
) {

    @GetMapping("/rennes-tech-next.ics", produces = ["text/calendar"])
    fun next(): ResponseEntity<ByteArray> {
        val events = eventList.lastMonthAndNextSixMonth()
        if (events.isEmpty()) return emptyIcs()

        val groupsById = groupList.list().associateBy { it.id }

        // Simple calendar build avoiding generic inference issues
        var calendar = Calendar()
            .withProdId("-//Rennes Tech//Events//FR")
            .withProperty(Name("Rennes.tech"))
            .withProperty(ImmutableVersion.VERSION_2_0)
            .withProperty(ImmutableCalScale.GREGORIAN)

        calendar = events
            .mapNotNull { it.toVevent(groupsById) }
            .fold(calendar) { cal, e -> cal.withComponent(e) }

        val out = ByteArrayOutputStream()
        CalendarOutputter().output(calendar.fluentTarget, out)

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rennes-tech-next.ics\"")
            .contentType(MediaType.parseMediaType("text/calendar; charset=UTF-8"))
            .body(out.toByteArray())
    }

    private fun Event.toVevent(groupsById: Map<GroupId, Group>): VEvent? {
        val group = groupsById[groupId] ?: return null
        val start = startDate
        val end = endDate ?: startDate.plusHours(2)
        val description = markdownToPlain.convert(description)

        var vevent = VEvent(start, end, title)
            .withProperty(Uid(id.id.toString()))
            .withProperty(Description(description))

        if (providerId != null) {
            vevent = vevent.withProperty(Url(URI.create(urlMapper.mapEvent(this, group))))
        }

        (venue as? PhysicalVenue)?.let { venue ->
            vevent = vevent.withProperty(Location(venue.completeAddress))
            if (venue.latitude != null && venue.longitude != null) {
                vevent =
                    vevent.withProperty(Geo(venue.latitude, venue.longitude))
            }
        }

        return vevent.fluentTarget as VEvent
    }

    private fun emptyIcs(): ResponseEntity<ByteArray> =
        ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rennes-tech-next.ics\"")
            .contentType(MediaType.parseMediaType("text/calendar; charset=UTF-8"))
            .body(ByteArray(0))


}
