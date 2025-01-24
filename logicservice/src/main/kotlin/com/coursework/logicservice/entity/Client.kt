package com.coursework.logicservice.entity
import jakarta.persistence.*

@Entity
@Table(name = "clients")
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val phone: String,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val rating: Double = 0.0
)