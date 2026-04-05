package org.breizhcamp.rennes.tech.infrastructure.groups

import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.entities.Group
import org.breizhcamp.rennes.tech.domain.ports.GroupPort
import org.breizhcamp.rennes.tech.infrastructure.groups.dto.GroupListConfig
import org.springframework.core.io.ClassPathResource
import tools.jackson.dataformat.yaml.YAMLMapper
import tools.jackson.module.kotlin.kotlinModule
import tools.jackson.module.kotlin.readValue

@Adapter
class GroupAdapter: GroupPort {
    private val yamlMapper = YAMLMapper.builder()
        .addModule(kotlinModule())
        .build()

    override fun list(): List<Group> = yamlMapper
        .readValue<GroupListConfig>(ClassPathResource("groups.yaml").inputStream).groups
        .filter { it.active != false }
        .map { it.toDomain() }


}
