package com.coursework.logicservice.ext

import com.coursework.logic.LogicServiceOuterClass
import com.coursework.logicservice.dto.TripDTO
import com.coursework.logicservice.entity.Trip
import com.coursework.logicservice.repository.*
import java.time.LocalDateTime


fun Trip.toDTO() = TripDTO(
    id = this.id,
    clientId = this.client.id,
    driverId = this.driver.id,
    carId = this.car.id,
    companyId = this.company.id,
    tariffId = this.tariff.id,
    driverRate = this.driverRate,
    clientRate = this.clientRate,
    service = this.service,
    price = this.price,
    startDate = this.startDate, // Преобразуем в ISO-формат
    endDate = this.endDate      // Преобразуем в ISO-формат
)
fun TripDTO.toEntity(
    clientRepository: ClientRepository,
    driverRepository: DriverRepository,
    companyRepository: CompanyRepository,
    tariffRepository: TariffRepository
): Trip {
    val client = clientRepository.findById(clientId)
        .orElseThrow { IllegalArgumentException("Client with id $clientId not found") }
    val driver = driverRepository.findById(driverId)
        .orElseThrow { IllegalArgumentException("Driver with id $driverId not found") }
    val company = companyRepository.findById(companyId)
        .orElseThrow { IllegalArgumentException("Company with id $companyId not found") }
    val car = driver.car
    val tariff = tariffRepository.findById(tariffId)
        .orElseThrow { IllegalArgumentException("Tariff with id $tariffId not found") }

    return Trip(
        id = this.id,
        client = client,
        driver = driver,
        car = car!!,
        company = company,
        tariff = tariff,
        driverRate = this.driverRate,
        clientRate = this.clientRate,
        service = this.service,
        price = this.price,
        startDate = this.startDate,
        endDate = this.endDate
    )
}
fun LogicServiceOuterClass.TripDTO.toEntity(
    clientRepository: ClientRepository,
    driverRepository: DriverRepository,
    companyRepository: CompanyRepository,
    tariffRepository: TariffRepository
): Trip {
    val client = clientRepository.findById(clientId)
        .orElseThrow { IllegalArgumentException("Client with id $clientId not found") }
    val driver = driverRepository.findById(driverId)
        .orElseThrow { IllegalArgumentException("Driver with id $driverId not found") }
    val company = companyRepository.findById(companyId)
        .orElseThrow { IllegalArgumentException("Company with id $companyId not found") }
    val car = driver.car
    val tariff = tariffRepository.findById(tariffId)
        .orElseThrow { IllegalArgumentException("Tariff with id $tariffId not found") }

    return Trip(
        id = this.id,
        client = client,
        driver = driver,
        car = car!!,
        company = company,
        tariff = tariff,
        driverRate = this.driverRate,
        clientRate = this.clientRate,
        service = this.service,
        price = this.price.toBigDecimal(),
        startDate = LocalDateTime.parse(this.startDate),
        endDate = LocalDateTime.parse(this.endDate)
    )
}

fun TripDTO.toProto() = LogicServiceOuterClass.TripDTO.newBuilder()
    .setId(id)
    .setClientId(clientId)
    .setDriverId(driverId)
    .setCarId(carId)
    .setCompanyId(companyId)
    .setTariffId(tariffId)
    .setDriverRate(driverRate ?: 0.0) // Если null, устанавливаем значение по умолчанию
    .setClientRate(clientRate ?: 0.0) // Если null, устанавливаем значение по умолчанию
    .setService(service)
    .setPrice(price.toPlainString())  // BigDecimal преобразуем в строку
    .setStartDate(startDate.toString())
    .setEndDate(endDate.toString())
    .build()
