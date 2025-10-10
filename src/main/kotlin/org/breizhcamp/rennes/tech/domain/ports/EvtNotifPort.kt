package org.breizhcamp.rennes.tech.domain.ports

/**
 * Send event internal notification for handling by other services
 */
interface EvtNotifPort {
    fun send(evt: Any)
}
