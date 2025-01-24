package com.coursework.mainservice.config

import com.coursework.mainservice.repository.UserRepository
import com.coursework.mainservice.service.JwtUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter,
    private val userRepository: UserRepository
) {
    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
    @Bean
    fun authenticationManager(
        http: HttpSecurity
    ): AuthenticationManager {
        val builder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        builder.authenticationProvider(daoAuthenticationProvider())
        return builder.build()
    }
    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService(userRepository))
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }
    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService =
        JwtUserDetailsService(userRepository)
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .cors{(it.disable())}
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/logic").permitAll()
                    .requestMatchers("/download").permitAll()
                    .requestMatchers("/main").permitAll()
                    .requestMatchers("/main/map").permitAll()
                    .requestMatchers("/api/logic/**").permitAll()
                    .requestMatchers("/api/auth/login").permitAll()
                    .requestMatchers("/api/auth/signup").permitAll()
                    .requestMatchers("/auth/login").permitAll()
                    .requestMatchers("/send-notification").permitAll()
                    .requestMatchers("/get-notifications").permitAll()
                    .requestMatchers("/auth/signup").permitAll()

                    .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/v3/api-docs.yaml"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

}