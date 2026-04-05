package org.breizhcamp.rennes.tech.infrastructure.db

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.breizhcamp.rennes.tech.data.EventData
import org.breizhcamp.rennes.tech.data.EventDbData
import org.breizhcamp.rennes.tech.data.GroupData
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.infrastructure.db.repos.EventRepo
import org.breizhcamp.rennes.tech.infrastructure.db.repos.PhysicalVenueRepo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Instant
import java.time.ZoneOffset

@ExtendWith(MockKExtension::class)
class EventAdapterTest {

    @RelaxedMockK
    private lateinit var eventRepo: EventRepo

    @RelaxedMockK
    private lateinit var physicalVenueRepo: PhysicalVenueRepo

    @InjectMockKs
    private lateinit var adapter: EventAdapter

    @Test
    fun `should add non existing event`() {
        /* ****  GIVEN  **** */
        val since = Instant.parse("2025-10-10T00:00:00Z")
        val group = GroupData.python
        val event = EventData.event()

        every { eventRepo.findAllAfter(any()) } returns emptyList()
        every { eventRepo.save(any()) } returnsArgument 0
        every { physicalVenueRepo.save(any()) } returnsArgument 0
        justRun { eventRepo.deleteAll(any()) }

        /* ****  WHEN  **** */
        val res = adapter.syncNext(since, listOf(event), listOf(group))

        /* ****  THEN  **** */
        assertThat(res.created).hasSize(1)
        assertThat(res.updated).isEmpty()
        assertThat(res.deleted).isEmpty()

        val created = res.created.first()
        assertThat(created.title).isEqualTo(event.title)
        assertThat(created.description).isEqualTo(event.description)
        assertThat(created.thumbnailUrl).isEqualTo(event.thumbnailUrl)
        assertThat(created.providerId).isEqualTo(event.providerId)
        assertThat(created.groupId).isEqualTo(event.groupId)
        assertThat(created.venue).isInstanceOf(PhysicalVenue::class)

        // adapter always invokes both repo methods
        verify(exactly = 1) { eventRepo.save(any()) }
        verify(exactly = 1) { physicalVenueRepo.save(any()) }
    }

    @Test
    fun `should update existing event`() {
        /* ****  GIVEN  **** */
        val since = Instant.parse("2025-10-10T00:00:00Z")
        val group = GroupData.python

        val existingDb = EventDbData.eventDb(
            title = "Old title",
            description = "Old description",
            thumbnailUrl = "https://old.example/img.jpeg"
        )
        val newEvent = EventData.event(
            title = "Python - a kind of magic ! (updated)",
            description = "Updated description",
            thumbnailUrl = "https://secure.meetupstatic.com/photos/event/8/6/d/0/highres_530494512.jpeg"
        )

        every { eventRepo.findAllAfter(any()) } returns listOf(existingDb)
        every { physicalVenueRepo.save(any()) } returnsArgument 0
        justRun { eventRepo.deleteAll(any()) }

        /* ****  WHEN  **** */
        val res = adapter.syncNext(since, listOf(newEvent), listOf(group))

        /* ****  THEN  **** */
        assertThat(res.created).isEmpty()
        assertThat(res.deleted).isEmpty()
        assertThat(res.updated).hasSize(1)

        val updated = res.updated.first()
        assertThat(updated.title).isEqualTo(newEvent.title)
        assertThat(updated.description).isEqualTo(newEvent.description)
        assertThat(updated.thumbnailUrl).isEqualTo(newEvent.thumbnailUrl)
        assertThat(updated.providerId).isEqualTo(newEvent.providerId)
        assertThat(updated.groupId).isEqualTo(newEvent.groupId)
        assertThat(updated.startDate).isEqualTo(newEvent.startDate)
        assertThat(updated.endDate).isEqualTo(newEvent.endDate)

        verify(exactly = 1) { eventRepo.deleteAll(any()) }
    }

    @Test
    fun `should delete inexistent event if start date after margin`() {
        /* ****  GIVEN  **** */
        val since = Instant.parse("2025-10-10T00:00:00Z")
        val startAfterMargin = since.atOffset(ZoneOffset.UTC).plusMinutes(30)
        val existingDb = EventDbData.eventDb(start = startAfterMargin, end = startAfterMargin.plusHours(2))

        every { eventRepo.findAllAfter(any()) } returns listOf(existingDb)
        justRun { eventRepo.deleteAll(any()) }

        /* ****  WHEN  **** */
        val res = adapter.syncNext(since, emptyList(), listOf(GroupData.python))

        /* ****  THEN  **** */
        assertThat(res.created).isEmpty()
        assertThat(res.updated).isEmpty()
        assertThat(res.deleted).hasSize(1)

        verify(exactly = 1) { eventRepo.deleteAll(any()) }
    }

    @Test
    fun `should not delete inexistent event if start date inside margin`() {
        /* ****  GIVEN  **** */
        val since = Instant.parse("2025-10-10T00:00:00Z")
        val startInsideMargin = since.atOffset(ZoneOffset.UTC).plusMinutes(10)
        val existingDb = EventDbData.eventDb(start = startInsideMargin, end = startInsideMargin.plusHours(2))

        every { eventRepo.findAllAfter(any()) } returns listOf(existingDb)
        justRun { eventRepo.deleteAll(any()) }

        /* ****  WHEN  **** */
        val res = adapter.syncNext(since, emptyList(), listOf(GroupData.python))

        /* ****  THEN  **** */
        assertThat(res.created).isEmpty()
        assertThat(res.updated).isEmpty()
        assertThat(res.deleted).isEmpty()

        verify(exactly = 1) { eventRepo.deleteAll(any()) }
    }
}
