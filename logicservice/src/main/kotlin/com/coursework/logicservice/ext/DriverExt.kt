package com.coursework.logicservice.ext

import com.coursework.logic.LogicServiceOuterClass
import com.coursework.logicservice.dto.ActiveDriverDTO
import com.coursework.logicservice.dto.DriverDTO
import com.coursework.logicservice.entity.ActiveDriver
import com.coursework.logicservice.entity.Driver

fun Driver.toDTO() = DriverDTO(
    id = this.id,
    phone = this.phone,
    name = this.name,
    surname = this.surname,
    email = this.email,
    password = this.password,
    rating = this.rating,
    ready = this.ready

)


fun DriverDTO.toProto() = LogicServiceOuterClass.DriverDTO.newBuilder()
    .setId(id!!)
    .setPhone(phone)
    .setName(name)
    .setSurname(surname)
    .setEmail(email)
    .setPassword(password)
    .setRating(rating)
    .setReady(ready)
    .build()