package com.coursework.logicservice.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "active_drivers")
data class ActiveDriver(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    val driver: Driver,

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    val company: Company,

    @Column(nullable = false)
    var ready: Boolean = false,

    @Column(name = "start_date", nullable = false)
    var startDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "end_date")
    var endDate: LocalDateTime? = null
)
