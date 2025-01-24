package com.coursework.logicservice.repository

import com.coursework.logicservice.entity.ActiveDriver
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActiveDriversRepository: JpaRepository<ActiveDriver,Long> {
    fun findAllByReadyTrue(): List<ActiveDriver>
}