package org.breizhcamp.rennes.tech.application.rest

import org.breizhcamp.rennes.tech.application.rest.dto.GroupApi
import org.breizhcamp.rennes.tech.application.rest.dto.toApi
import org.breizhcamp.rennes.tech.domain.use_cases.GroupList
import org.breizhcamp.rennes.tech.domain.use_cases.UrlMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@RestController
@RequestMapping("/api/v1/groups", produces = ["application/json"])
@Tag(name = "groups", description = "Group related operations")
class GroupApiCtrl(
    private val groupList: GroupList,
    private val urlMapper: UrlMapper,
) {

    @Operation(summary = "List groups", description = "Returns list of groups")
    @GetMapping
    fun list(): List<GroupApi> =
        groupList.list().map { it.toApi(urlMapper.mapGroup(it)) }
}
