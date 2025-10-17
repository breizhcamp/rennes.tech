package org.breizhcamp.rennes.tech.domain.use_cases

import org.breizhcamp.rennes.tech.config.annotations.UseCase
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.ports.CachePort
import org.breizhcamp.rennes.tech.domain.ports.EventPort
import org.breizhcamp.rennes.tech.domain.ports.TimePort

@UseCase
class EventList(
    private val timePort: TimePort,
    private val eventPort: EventPort,
    private val cachePort: CachePort,
) {
    companion object {
        const val EVT_CACHE = "events"
    }

    fun lastMonthAndNextSixMonth(): List<Event> {
        val now = timePort.nowZoned()
        val lastMonth = now.minusMonths(1)
        val sixMonth = now.plusMonths(6)
        cachePort.clean<Event>(EVT_CACHE) { it.startDate.isAfter(lastMonth) && it.startDate.isBefore(sixMonth) }
        return cachePort.get(EVT_CACHE) { eventPort.listAfter(since = lastMonth.toInstant()) }
    }

    fun displayedNext(): List<Event> {
        val now = timePort.nowZoned()
        return lastMonthAndNextSixMonth().filter { (it.endDate ?: it.startDate.plusHours(3)).isAfter(now) }
    }
}
