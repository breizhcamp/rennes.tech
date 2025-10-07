package org.breizhcamp.rennes.tech.application.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeCtrl {

    @GetMapping
    fun home(): String {
        return "index"
    }

}
