package org.breizhcamp.rennes.tech.domain.ports

interface TimePort {

    fun nowZoned(): java.time.ZonedDateTime

}
