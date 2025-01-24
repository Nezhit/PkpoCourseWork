package com.coursework.logicservice.entity
import jakarta.persistence.*
@Entity
@Table(name = "cars")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val color: String,
    val mark: String,
    val number: String,

    @OneToOne
    @JoinColumn(name = "driver_id")

    val driver: Driver
)