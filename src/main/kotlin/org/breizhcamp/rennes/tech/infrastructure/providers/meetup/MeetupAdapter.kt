package org.breizhcamp.rennes.tech.infrastructure.providers.meetup

import org.breizhcamp.rennes.tech.config.BackConfig
import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.entities.*
import org.breizhcamp.rennes.tech.domain.ports.ProviderPort
import org.breizhcamp.rennes.tech.infrastructure.providers.meetup.dto.*
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import java.util.*

@Adapter
class MeetupAdapter(
    private val config: BackConfig,
): ProviderPort {
    private val client = createClient()

    override fun supports() = GroupProviderType.MEETUP

    override fun retrieveEvents(group: Group): List<Event> {
        val req = GqlReq(
            operationName = "groupHome",
            variables = mapOf("includePrivateInfo" to false, "urlname" to group.providerId),
            extensions = GqlReqExtensions(PersistedQuery("2b9fa4f50ca43ca190e283cfad4b46706f423df7d52c2accb2b4ae01c9a24540"))
        )

        return client.getGroupHome(req)
            .data.groupByUrlname.upcomingEvents.edges
            ?.mapNotNull { it.node.toEvent(group) }
            ?: return emptyList()
    }

    private fun MeetupEvent.toEvent(group: Group): Event? = this.venue?.toPhysicalVenue()?.let { venue ->
        Event(
            id = EventId(UUID.randomUUID()),
            title = this.title,
            description = this.description ?: "",
            startDate = this.dateTime.atZoneSameInstant(group.timezone),
            endDate = this.endTime?.atZoneSameInstant(group.timezone),
            thumbnailUrl = this.featuredEventPhoto?.highResUrl,
            providerId = this.id,
            groupId = group.id,
            venue = venue
        )
    }

    private fun MeetupVenue.toPhysicalVenue() = PhysicalVenue(
        name = this.name,
        address = this.address,
        city = this.city,
        country = this.country,
        latitude = null,
        longitude = null
    )

    private fun createClient(): MeetupClient {
        val adapter = RestClientAdapter.create(RestClient.builder().baseUrl(config.meetup.baseUrl).build())
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()
        return factory.createClient(MeetupClient::class.java)
    }
}
