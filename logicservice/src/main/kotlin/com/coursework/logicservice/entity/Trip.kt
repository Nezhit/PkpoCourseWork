package com.coursework.logicservice.entity
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "trips")
data class Trip(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    val client: Client,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    val driver: Driver,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    val car: Car,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    val company: Company,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff_id")
    val tariff: Tariff,

    val driverRate: Double?,
    val clientRate: Double?,
    val service: String,
    val price: BigDecimal,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)
