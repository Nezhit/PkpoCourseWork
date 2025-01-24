package com.coursework.mainservice.dto

data class DriverDTO (
    val id: Long = 0,
    var name: String,
    var surname: String,
    var phone: String,
    var email: String,
    var password: String,
    var rating: Double = 0.0,
    var ready: Boolean
)