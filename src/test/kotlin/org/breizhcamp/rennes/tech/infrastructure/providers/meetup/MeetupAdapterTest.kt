package org.breizhcamp.rennes.tech.infrastructure.providers.meetup

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.breizhcamp.rennes.tech.config.BackConfig
import org.breizhcamp.rennes.tech.data.GroupData
import org.breizhcamp.rennes.tech.domain.entities.Event
import org.breizhcamp.rennes.tech.domain.entities.PhysicalVenue
import org.breizhcamp.rennes.tech.utils.NoLogWireMock
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.wiremock.spring.ConfigureWireMock
import org.wiremock.spring.EnableWireMock
import org.wiremock.spring.InjectWireMock
import java.time.ZonedDateTime

@SpringBootTest(classes = [MeetupTestConfig::class])
@EnableConfigurationProperties(BackConfig::class)
@EnableWireMock(ConfigureWireMock(name = "meetup-mock", baseUrlProperties = ["rennes.tech.back.meetup.baseUrl"], configurationCustomizers = [NoLogWireMock::class]))
class MeetupAdapterTest {

    @InjectWireMock("meetup-mock")
    lateinit var wiremock: WireMockServer

    @Autowired
    lateinit var meetupAdapter: MeetupAdapter

    @Test
    fun `should retrieve and parse meetup event`() {
        /* ****  GIVEN  **** */
        val group = GroupData.python
        val bodyIn = requireNotNull(javaClass.getResourceAsStream("/meetup.json"))
        wiremock.stubFor(
            post(urlEqualTo("/gql2"))
                .withRequestBody(matchingJsonPath("$.operationName", equalTo("groupHome")))
                .withRequestBody(matchingJsonPath("$.variables.urlname", equalTo(group.id.id)))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyIn.readAllBytes())
                )
        )

        /* ****  WHEN  **** */
        val events = meetupAdapter.retrieveEvents(group)

        /* ****  THEN  **** */
        val tz = group.timezone.id
        assertThat(events).hasSize(1)
        assertThat(events[0]).all {
            prop(Event::title).isEqualTo("Python - a kind of magic !")
            prop(Event::description).isEqualTo("Un peu de magie pour cette session Python Rennes de rentrée avec 2 interventions :\n\n* **Python et la magie du typage statique**\n\nPar **Florian Strzelecki**")
            prop(Event::startDate).isEqualTo(ZonedDateTime.parse("2025-10-15T18:45:00+02:00[$tz]"))
            prop(Event::endDate).isEqualTo(ZonedDateTime.parse("2025-10-15T20:45:00+02:00[$tz]"))
            prop(Event::thumbnailUrl).isEqualTo("https://secure.meetupstatic.com/photos/event/8/6/d/0/highres_530494512.jpeg")
            prop(Event::providerId).isEqualTo("311313392")
            prop(Event::groupId).isEqualTo(group.id)
        }
        assertThat(events[0].venue).isInstanceOf(PhysicalVenue::class).all {
            prop(PhysicalVenue::name).isEqualTo("Hellowork")
            prop(PhysicalVenue::address).isEqualTo("2 Rue de la Mabilais")
            prop(PhysicalVenue::city).isEqualTo("Rennes")
            prop(PhysicalVenue::country).isEqualTo("fr")
            prop(PhysicalVenue::latitude).isNull()
            prop(PhysicalVenue::longitude).isNull()
        }
    }
}