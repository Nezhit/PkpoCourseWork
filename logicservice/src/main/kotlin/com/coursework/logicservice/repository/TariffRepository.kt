package com.coursework.logicservice.repository

import com.coursework.logicservice.entity.Tariff
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TariffRepository:JpaRepository<Tariff,Long> {
    fun findAllByCompanyId(companyId: Long): List<Tariff>
}