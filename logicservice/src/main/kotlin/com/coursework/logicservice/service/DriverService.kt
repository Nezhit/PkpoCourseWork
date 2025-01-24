package com.coursework.logicservice.service

import com.coursework.logicservice.entity.ActiveDriver
import com.coursework.logicservice.entity.Driver
import com.coursework.logicservice.repository.ActiveDriversRepository
import com.coursework.logicservice.repository.DriverRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class DriverService(
    private val driverRepository: DriverRepository,
    private val activeDriverRepository: ActiveDriversRepository
) {

    // Получить список всех водителей
    fun getAllDrivers(): List<Driver> {
        return driverRepository.findAll()
    }

    // Получить список активных водителей
    fun getActiveDrivers(): List<ActiveDriver> {
        return activeDriverRepository.findAllByReadyTrue()
    }

    // Получить список неактивных водителей
    fun getInactiveDrivers(): List<Driver> {
        val activeDriverIds = activeDriverRepository.findAllByReadyTrue().map { it.driver.id }
        return driverRepository.findAll().filter { it.id !in activeDriverIds }
    }

    // Редактировать водителя
    fun updateDriver(driverId: Long, updatedDriver: Driver): Driver {
        val driver = driverRepository.findById(driverId)
            .orElseThrow { EntityNotFoundException("Driver with ID $driverId not found") }

        driver.apply {
            name = updatedDriver.name
            surname = updatedDriver.surname
            phone = updatedDriver.phone
            email = updatedDriver.email
            password = updatedDriver.password
            rating = updatedDriver.rating
        }
        return driverRepository.save(driver)
    }
    fun updateReadyDriver(driverId: Long, ready: Boolean){
        val driver = driverRepository.findById(driverId)
            .orElseThrow { EntityNotFoundException("Driver with ID $driverId not found") }
        driver.ready = ready
        driverRepository.save(driver)
    }
    // Удалить водителя
    fun deleteDriver(driverId: Long) {
        driverRepository.deleteById(driverId)
    }
}
