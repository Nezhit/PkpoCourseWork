package com.coursework.mainservice.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/auth")
class AuthPageController {

    @GetMapping("/login")
    fun showLoginPage(): String {
        return "login"
    }

    @GetMapping("/signup")
    fun showSignupPage(): String {
        return "signup"
    }
}
