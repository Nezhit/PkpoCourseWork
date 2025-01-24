package com.coursework.mainservice.dto

import jakarta.persistence.*
import java.math.BigDecimal

class TariffDTO (
    val id: Long = 0,
    var name: String,
    var value: BigDecimal,
    var companyId: Long,
    var companyName: String
){
}
