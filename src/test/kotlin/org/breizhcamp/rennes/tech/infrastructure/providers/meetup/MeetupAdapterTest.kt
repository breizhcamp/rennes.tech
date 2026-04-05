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
                .withRequestBody(matchingJsonPath("$.operationName", equalTo("getUpcomingGroupEvents")))
                .withRequestBody(matchingJsonPath("$.variables.urlname", equalTo(group.providerId)))
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
        assertThat(events).hasSize(9)
        assertThat(events[0]).all {
            prop(Event::title).isEqualTo("[LYON] IT Women Talks & Networking ")
            prop(Event::description).isEqualTo("**Participez à notre prochaine conférence IT Women Talks**, organisée par l’association **Yeeso**, pour mettre en lumière les talents féminins de la Tech et favoriser l’échange entre toutes et tous !\n\n✨ **Sponsor** : Le Wagon ([page linkedin](https://www.linkedin.com/school/le-wagon/about/))")
            prop(Event::startDate).isEqualTo(ZonedDateTime.parse("2026-02-24T19:00:00+01:00[$tz]"))
            prop(Event::endDate).isEqualTo(ZonedDateTime.parse("2026-02-24T21:00:00+01:00[$tz]"))
            prop(Event::thumbnailUrl).isEqualTo("https://secure.meetupstatic.com/photos/event/6/d/f/d/highres_529828157.jpeg")
            prop(Event::providerId).isEqualTo("313204642")
            prop(Event::groupId).isEqualTo(group.id)
        }
        assertThat(events[0].venue).isInstanceOf(PhysicalVenue::class).all {
            prop(PhysicalVenue::name).isEqualTo("Le Wagon Lyon")
            prop(PhysicalVenue::address).isEqualTo("20 rue des Capucins")
            prop(PhysicalVenue::city).isEqualTo("Lyon")
            prop(PhysicalVenue::country).isEqualTo("fr")
            prop(PhysicalVenue::latitude).isNull()
            prop(PhysicalVenue::longitude).isNull()
        }
    }
}
