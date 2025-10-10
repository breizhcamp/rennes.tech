package org.breizhcamp.rennes.tech.application.schedules

import org.breizhcamp.rennes.tech.config.BackConfig
import org.breizhcamp.rennes.tech.domain.use_cases.SyncEvent
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class SyncSchedule(
    private val backConfig: BackConfig,
    private val syncEvent: SyncEvent,
) {

    @Scheduled(initialDelay = 10, fixedDelay = 60 * 60, timeUnit = TimeUnit.SECONDS)
    fun syncEvents() {
        if (!backConfig.sync.enabled) return
        syncEvent.sync()
    }

}
