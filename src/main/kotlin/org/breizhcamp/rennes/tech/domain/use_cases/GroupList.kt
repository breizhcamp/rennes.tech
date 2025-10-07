package org.breizhcamp.rennes.tech.domain.use_cases

import org.breizhcamp.rennes.tech.config.annotations.UseCase
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.ports.GroupPort

@UseCase
class GroupList(
    private val groupPort: GroupPort,
) {

    fun list(): List<Group> = groupPort.list()

}
