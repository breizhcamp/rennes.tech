package org.breizhcamp.rennes.tech.infrastructure.groups

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.ports.GroupPort
import org.breizhcamp.rennes.tech.infrastructure.groups.dto.GroupListConfig
import org.springframework.core.io.ClassPathResource

@Adapter
class GroupAdapter(
    private val yamlMapper: ObjectMapper,
): GroupPort {
    override fun list(): List<Group> = yamlMapper
        .readValue<GroupListConfig>(ClassPathResource("groups.yaml").inputStream).groups
        .filter { it.active != false }
        .map { it.toDomain() }


}
