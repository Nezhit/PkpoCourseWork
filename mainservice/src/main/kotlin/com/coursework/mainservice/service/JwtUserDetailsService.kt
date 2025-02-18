package com.coursework.mainservice.service

import com.coursework.mainservice.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findUserByName(username!!)
            ?: throw UsernameNotFoundException("User $username not found!")

        return User.builder()
            .username(user.name)
            .password(user.password)
            .roles(user.role.name)
            .build()
    }

}