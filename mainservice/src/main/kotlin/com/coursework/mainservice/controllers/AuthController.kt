package com.coursework.mainservice.controllers

import com.coursework.mainservice.dto.AuthRequest
import com.coursework.mainservice.dto.SignupRequest
import com.coursework.mainservice.service.AuthService
import com.coursework.mainservice.service.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(authService.authenticate(request))
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody signupRequest: SignupRequest):ResponseEntity<Any>{
        return ResponseEntity.ok(authService.signUp(signupRequest))
    }
}