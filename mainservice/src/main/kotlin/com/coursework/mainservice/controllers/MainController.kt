package com.coursework.mainservice.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/main")
class MainController {
    @GetMapping
    fun mainPage():String{
        return "main"
    }
    @GetMapping("/map")
    fun mapPage():String{
        return "map"
    }
}