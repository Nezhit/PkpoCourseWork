package com.coursework.logicservice.repository

import com.coursework.logicservice.entity.Driver
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DriverRepository :JpaRepository<Driver,Long> {
}