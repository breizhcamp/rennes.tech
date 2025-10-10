package org.breizhcamp.rennes.tech.utils

import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.wiremock.spring.ConfigureWireMock
import org.wiremock.spring.WireMockConfigurationCustomizer

class NoLogWireMock: WireMockConfigurationCustomizer {
    override fun customize(configuration: WireMockConfiguration?, options: ConfigureWireMock?) {
        configuration?.stubRequestLoggingDisabled(true)
    }
}
