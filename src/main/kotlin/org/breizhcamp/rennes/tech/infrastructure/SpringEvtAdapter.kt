package org.breizhcamp.rennes.tech.infrastructure

import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.ports.EvtNotifPort
import org.springframework.context.ApplicationEventPublisher

@Adapter
class SpringEvtAdapter(
    private val appEvtPublisher: ApplicationEventPublisher,
): EvtNotifPort {
    override fun send(evt: Any) = appEvtPublisher.publishEvent(evt)
}
