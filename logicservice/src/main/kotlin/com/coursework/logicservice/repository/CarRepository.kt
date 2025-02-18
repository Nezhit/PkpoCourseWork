package com.coursework.logicservice.repository

import com.coursework.logicservice.entity.Car
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CarRepository :JpaRepository<Car,Long> {
}