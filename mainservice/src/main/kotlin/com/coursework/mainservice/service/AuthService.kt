package com.coursework.mainservice.service

import com.coursework.mainservice.dto.AuthRequest
import com.coursework.mainservice.dto.SignupRequest
import com.coursework.mainservice.entity.Role
import com.coursework.mainservice.entity.User
import com.coursework.mainservice.exceptions.EntityAlreadyExists
import com.coursework.mainservice.exceptions.InvalidCredentialsException
import com.coursework.mainservice.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService (
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
){
    fun authenticate(request: AuthRequest) : Map<String, String>{
        val authToken = UsernamePasswordAuthenticationToken(request.username, request.password)
         try {
            val authentication = authenticationManager.authenticate(authToken)
            if (authentication.isAuthenticated) {
                val token = jwtService.generateToken(request.username)
                return mapOf("token" to token)
            } else {
                throw InvalidCredentialsException()
            }
        } catch (e: AuthenticationException) {
             throw InvalidCredentialsException()
        }
    }
    fun signUp(request: SignupRequest): Map<String, String> {
        if (userRepository.existsByName(request.username)) {
            throw EntityAlreadyExists("User with the same username already exists")
        }
        val hashedPassword = passwordEncoder.encode(request.password)
        val newUser = User(
            id = UUID.randomUUID(),
            name = request.username,
            password = hashedPassword,
            role = Role.USER
        )
        userRepository.save(newUser)
        val token = jwtService.generateToken(request.username)
        return mapOf("token" to token)
    }
}