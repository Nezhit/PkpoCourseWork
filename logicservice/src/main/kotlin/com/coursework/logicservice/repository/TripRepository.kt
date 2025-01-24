package com.coursework.logicservice.repository

import com.coursework.logicservice.entity.Trip
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TripRepository:JpaRepository<Trip,Long> {
}