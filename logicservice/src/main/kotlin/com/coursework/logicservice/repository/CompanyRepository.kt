package com.coursework.logicservice.repository

import com.coursework.logicservice.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository :JpaRepository<Company,Long> {
}