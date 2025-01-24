package com.coursework.logicservice.service

import com.coursework.logicservice.repository.TripRepository
import org.springframework.stereotype.Service

@Service
class RatingService(
    private val tripRepository: TripRepository
) {

    // Получить список оценок поездок (водителя и клиента)
    fun getTripRatings(): List<Pair<Double?, Double?>> {
        return tripRepository.findAll().map { trip ->
            Pair(trip.driverRate, trip.clientRate)
        }
    }
}
