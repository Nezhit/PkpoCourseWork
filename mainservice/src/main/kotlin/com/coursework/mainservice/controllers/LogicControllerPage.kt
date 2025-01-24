package com.coursework.mainservice.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/logic")
class LogicControllerPage {
    @GetMapping
    fun showLogicPage(): String {
        return "logic"
    }
}
