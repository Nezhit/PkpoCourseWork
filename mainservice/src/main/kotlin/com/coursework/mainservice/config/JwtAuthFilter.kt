package com.coursework.mainservice.config

import com.coursework.mainservice.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter (
    private val jwtService: JwtService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.servletPath
        if (path == "/api/auth/login") {
            // Пропускаем запрос без проверки токена
            filterChain.doFilter(request, response)
            return
        }
        val authHeader = request.getHeader("Authorization")
        if (authHeader?.startsWith("Bearer ") == true) {
            val token = authHeader.substring(7)
            if (jwtService.validateToken(token)) {
                val username = jwtService.extractUsername(token)
                if (username != null && SecurityContextHolder.getContext().authentication == null) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        emptyList()
                    )
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }

        filterChain.doFilter(request, response)
    }


}