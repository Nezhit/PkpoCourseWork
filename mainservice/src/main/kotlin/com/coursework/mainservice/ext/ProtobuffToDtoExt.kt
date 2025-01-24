package com.coursework.mainservice.ext

import com.coursework.logic.LogicServiceOuterClass
import com.coursework.mainservice.dto.ActiveDriverDTO
import com.coursework.mainservice.dto.DriverDTO
import com.coursework.mainservice.dto.TariffDTO
import com.coursework.mainservice.dto.TripDTO
import com.coursework.notification.Notification
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun LogicServiceOuterClass.ActiveDriverDTO.toDTO(): ActiveDriverDTO {
    return ActiveDriverDTO(
        id = this.id,
        name = this.name,
        surname = this.surname,
        phone = this.phone,
        email = this.email,
        rating = this.rating,
        ready = this.ready
    )
}
fun LogicServiceOuterClass.DriverDTO.toDTO(): DriverDTO {
    return DriverDTO(
        id = this.id,
        name = this.name,
        surname = this.surname,
        phone = this.phone,
        email = this.email,
        rating = this.rating,
        password = this.password,
        ready = this.ready
    )
}
fun DriverDTO.toProto(): LogicServiceOuterClass.DriverDTO {
    return LogicServiceOuterClass.DriverDTO.newBuilder()
        .setId(this.id)
        .setName(this.name)
        .setSurname(this.surname)
        .setPhone(this.phone)
        .setEmail(this.email)
        .setPassword(this.password)
        .setRating(this.rating)
        .build()
}
fun LogicServiceOuterClass.TariffDTO.toDTO(): TariffDTO {
    return TariffDTO(
        id = this.id,
        name = this.name,
        value = this.value.toBigDecimal(),
        companyId = this.companyId,
        companyName = this.companyName
    )
}
fun TariffDTO.toProto(): LogicServiceOuterClass.TariffDTO{
    return LogicServiceOuterClass.TariffDTO.newBuilder()
        .setId(this.id)
        .setName(this.name)
        .setValue(this.value.toDouble())
        .setCompanyId(this.companyId)
        .setCompanyName(this.companyName)
        .build()
}
fun LogicServiceOuterClass.TripDTO.toDTO(): TripDTO {
    return TripDTO(
        id = this.id,
        clientId = this.clientId,
        driverId = this.driverId,
        carId = this.carId,
        companyId = this.companyId,
        tariffId = this.tariffId,
        driverRate = this.driverRate,
        clientRate = this.clientRate,
        service = this.service,
        price = this.price.toBigDecimal(),
        startDate = LocalDateTime.parse(this.startDate), // ISO-формат (yyyy-MM-ddTHH:mm)
        endDate = LocalDateTime.parse(this.endDate)      // ISO-формат (yyyy-MM-ddTHH:mm)
    )
}
fun TripDTO.toProto(): LogicServiceOuterClass.TripDTO {
    return LogicServiceOuterClass.TripDTO.newBuilder()
        .setId(this.id)
        .setClientId(this.clientId)
        .setDriverId(this.driverId)
        .setCarId(this.carId)
        .setCompanyId(this.companyId)
        .setTariffId(this.tariffId)
        .setDriverRate(this.driverRate ?: 0.0) // Если null, устанавливаем значение по умолчанию
        .setClientRate(this.clientRate ?: 0.0) // Если null, устанавливаем значение по умолчанию
        .setService(this.service)
        .setPrice(this.price.toPlainString())  // BigDecimal преобразуем в строку
        .setStartDate(this.startDate.toString()) // LocalDateTime в строку ISO-формата
        .setEndDate(this.endDate.toString())    // LocalDateTime в строку ISO-формата
        .build()
}
fun TripDTO.toProtoNotification(): Notification.TripDTO {
    return Notification.TripDTO.newBuilder()
        .setId(this.id)
        .setClientId(this.clientId)
        .setDriverId(this.driverId)
        .setCarId(this.carId)
        .setCompanyId(this.companyId)
        .setTariffId(this.tariffId)
        .setDriverRate(this.driverRate ?: 0.0) // Если null, устанавливаем значение по умолчанию
        .setClientRate(this.clientRate ?: 0.0) // Если null, устанавливаем значение по умолчанию
        .setService(this.service)
        .setPrice(this.price.toPlainString())  // BigDecimal преобразуем в строку
        .setStartDate(this.startDate.toString()) // LocalDateTime в строку ISO-формата
        .setEndDate(this.endDate.toString())    // LocalDateTime в строку ISO-формата
        .build()
}