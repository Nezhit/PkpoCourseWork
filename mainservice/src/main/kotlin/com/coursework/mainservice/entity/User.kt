package com.coursework.mainservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "users")
data class User (
    @Id
    val id: UUID,
    val name: String,
    val password: String,
    val role: Role

)
enum class Role {
    USER, ADMIN
}