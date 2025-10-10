package org.breizhcamp.rennes.tech.infrastructure.providers.meetup

import org.breizhcamp.rennes.tech.infrastructure.providers.meetup.dto.GqlReq
import org.breizhcamp.rennes.tech.infrastructure.providers.meetup.dto.GroupHomeRes
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange

interface MeetupClient {

    @PostExchange("/gql2")
    fun getGroupHome(@RequestBody gqlReq: GqlReq): GroupHomeRes

}