package com.coursework.logicservice.dto

data class ActiveDriverDTO(
    val id: Long,
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val rating: Double,
    val ready: Boolean
)