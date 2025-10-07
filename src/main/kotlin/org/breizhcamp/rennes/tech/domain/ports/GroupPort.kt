package org.breizhcamp.rennes.tech.domain.ports

import org.breizhcamp.rennes.tech.domain.entities.Group

interface GroupPort {

    fun list(): List<Group>

}
