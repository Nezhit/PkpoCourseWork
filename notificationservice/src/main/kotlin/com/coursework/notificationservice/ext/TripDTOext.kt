package com.coursework.notificationservice.ext

import com.coursework.notification.Notification.TripDTO
import com.google.protobuf.ByteString
import java.math.BigDecimal
import java.time.LocalDateTime


fun TripDTO.toDto():com.coursework.notificationservice.dto.TripDTO{
    return com.coursework.notificationservice.dto.TripDTO(
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
         startDate = LocalDateTime.parse(this.startDate),
         endDate = LocalDateTime.parse(this.endDate)
    )
}
fun com.coursework.notificationservice.dto.TripDTO.toProto(): TripDTO {
    return TripDTO.newBuilder()
        .setId(id)
        .setClientId(clientId)
        .setDriverId(driverId)
        .setCarId(carId)
        .setCompanyId(companyId)
        .setTariffId(tariffId)
        .setDriverRate(driverRate ?: 0.0) // Если значение null, устанавливаем 0.0
        .setClientRate(clientRate ?: 0.0) // Если значение null, устанавливаем 0.0
        .setService(service)
        .setPrice(price.toPlainString()) // Преобразуем BigDecimal в строку
        .setStartDate(startDate.toString()) // Преобразуем LocalDateTime в ISO-строку
        .setEndDate(endDate.toString()) // Преобразуем LocalDateTime в ISO-строку
        .build()
}
fun ByteArray.toProto(): ByteString {
    return ByteString.copyFrom(this)
}