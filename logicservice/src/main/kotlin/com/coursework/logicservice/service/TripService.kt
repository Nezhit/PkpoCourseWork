package com.coursework.logicservice.service

import com.coursework.logicservice.dto.TripDTO
import com.coursework.logicservice.entity.Trip
import com.coursework.logicservice.ext.toEntity
import com.coursework.logicservice.repository.*
import org.springframework.stereotype.Service

@Service
class TripService(
    private val tripRepository: TripRepository,
    private val clientRepository: ClientRepository,
    private val driverRepository: DriverRepository,
    private val companyRepository: CompanyRepository,
    private val tariffRepository: TariffRepository
    ){
    fun getAllTrips(): List<Trip>{
        return tripRepository.findAll()
    }

    fun getLastTrips():List<Trip>{
        return tripRepository.findAll().sortedByDescending { it.id }.take(5)
    }
    fun saveTrip(trip: Trip){
        tripRepository.save(trip)
    }

}