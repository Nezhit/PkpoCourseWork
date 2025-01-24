package com.coursework.mainservice.controllers

import com.coursework.logic.LogicServiceOuterClass
import com.coursework.mainservice.dto.ActiveDriverDTO
import com.coursework.mainservice.dto.DriverDTO
import com.coursework.mainservice.dto.TariffDTO
import com.coursework.mainservice.dto.TripDTO
import com.coursework.mainservice.service.LogicServiceClient
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/logic")
class LogicController(private val logicServiceClient: LogicServiceClient) {

    // DriverService
    @GetMapping("/drivers/all")
    fun getAllDrivers(): List<DriverDTO> {
        return logicServiceClient.getAllDrivers()
    }

    @GetMapping("/drivers/active")
    fun getActiveDrivers(): List<ActiveDriverDTO> {
        return logicServiceClient.getActiveDrivers()
    }

    @GetMapping("/drivers/inactive")
    fun getInactiveDrivers(): List<DriverDTO> {
        return logicServiceClient.getInactiveDrivers()
    }

    @GetMapping("/last-trips")
    fun getLastTrips():List<TripDTO>{
        return logicServiceClient.getLastTrips()
    }
    @GetMapping("/trips")
    fun getTrips():List<TripDTO>{
        return logicServiceClient.getTrips()
    }

    @PostMapping("/add-trip")
    fun addTrip(@RequestBody tripDTO: TripDTO){
        logicServiceClient.saveTrip(tripDTO)
    }
    @PutMapping("/drivers/{id}")
    fun updateDriver(
        @PathVariable id: Long,
        @RequestBody updatedDriver: DriverDTO
    ): DriverDTO {
        return logicServiceClient.updateDriver(id, updatedDriver)
    }
    @PostMapping("/drivers/update-ready/{driverId}")
    fun updateReadyDriver(
        @PathVariable driverId : Long,
        @RequestBody ready: Boolean
    ){
        logicServiceClient.updateReadyDriver(driverId,ready)
    }

    @DeleteMapping("/drivers/{id}")
    fun deleteDriver(@PathVariable id: Long) {
        logicServiceClient.deleteDriver(id)
    }

    // FinancialAnalysisService
    @GetMapping("/financial/revenue")
    fun analyzeDailyRevenue(): List<Pair<String, Double>> {
        return logicServiceClient.analyzeDailyRevenue()
    }

    // RatingService
    @GetMapping("/ratings/trips")
    fun getTripRatings(): List<Pair<Double, Double>> {
        return logicServiceClient.getTripRatings()
    }

    // TariffService
    @GetMapping("/tariffs/company/{companyId}")
    fun getTariffsByCompany(@PathVariable companyId: Long): List<TariffDTO> {
        return logicServiceClient.getTariffsByCompany(companyId)
    }

    @PutMapping("/tariffs/{id}")
    fun updateTariff(
        @PathVariable id: Long,
        @RequestBody updatedTariff: TariffDTO
    ): TariffDTO {
        return logicServiceClient.updateTariff(id, updatedTariff)
    }
}
