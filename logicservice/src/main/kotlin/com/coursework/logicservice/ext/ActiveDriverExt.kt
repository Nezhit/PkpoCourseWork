package com.coursework.logicservice.ext

import com.coursework.logic.LogicServiceOuterClass
import com.coursework.logicservice.dto.ActiveDriverDTO
import com.coursework.logicservice.entity.ActiveDriver

fun ActiveDriver.toDTO() = ActiveDriverDTO(
    id = this.id,
    name = this.driver.name,
    surname = this.driver.surname,
    phone = this.driver.phone,
    email = this.driver.email,
    rating = this.driver.rating,
    ready = this.ready
)
//fun ActiveDriver.toProto() = LogicServiceOuterClass.ActiveDriverDTO.newBuilder()
//    .setId(id!!)
//    .setDriver(driver.toProto())
//    .setReady(ready)
//    .build()

fun ActiveDriverDTO.toProto() = LogicServiceOuterClass.ActiveDriverDTO.newBuilder()
    .setId(id!!)
    .setName(name)
    .setSurname(surname)
    .setPhone(phone)
    .setEmail(email)
    .setRating(rating)
    .setReady(ready)
    .build()

