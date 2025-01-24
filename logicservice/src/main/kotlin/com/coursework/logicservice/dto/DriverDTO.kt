package com.coursework.logicservice.dto

data class DriverDTO (
    val id: Long = 0,
    var phone: String,
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var rating: Double = 0.0,
    var ready: Boolean
)