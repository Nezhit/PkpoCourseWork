package com.coursework.notificationservice.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class TripDTO(
    val id: Long,
    val clientId: Long,
    val driverId: Long,
    val carId: Long,
    val companyId: Long,
    val tariffId: Long,
    val driverRate: Double?,
    val clientRate: Double?,
    val service: String,
    val price: BigDecimal,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)