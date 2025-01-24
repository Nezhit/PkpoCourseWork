package com.coursework.mainservice.service

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secret: String
) {

    private val signingKey: SecretKeySpec
        get() {
            val keyBytes: ByteArray = Base64.getDecoder().decode(secret)
            // println("Decoded secret: ${keyBytes.joinToString("") { "%02x".format(it) }}")
            return SecretKeySpec(keyBytes, 0, keyBytes.size, "HmacSHA256")
        }
    private val expirationTime = 1000 * 60 * 60 * 10 // 10 часов

    fun generateToken(username: String) : String{
        val now = Date()
        val expiryDate = Date(now.time + expirationTime)
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()
    }
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (ex: JwtException) {
            false
        }
    }

    fun extractUsername(token: String): String? {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .body
            claims.subject
        } catch (e: Exception) {
            null
        }
    }
}