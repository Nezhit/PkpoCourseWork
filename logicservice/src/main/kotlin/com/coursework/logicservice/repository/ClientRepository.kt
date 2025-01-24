package com.coursework.logicservice.repository

import com.coursework.logicservice.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client,Long> {
}