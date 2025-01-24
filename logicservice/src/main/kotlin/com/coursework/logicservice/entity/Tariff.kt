package com.coursework.logicservice.entity
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "tariffs")
data class Tariff(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var name: String,
    var value: BigDecimal,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    var company: Company
)
