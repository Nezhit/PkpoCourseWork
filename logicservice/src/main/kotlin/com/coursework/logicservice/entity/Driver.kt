package com.coursework.logicservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "drivers")
data class Driver(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var phone: String,
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var rating: Double = 0.0,
    var ready: Boolean,

    @OneToOne(mappedBy = "driver", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val car: Car? = null
)