package com.coursework.mainservice.repository

import com.coursework.mainservice.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User,UUID>{
    fun findUserByName(name: String) : User?
    fun existsByName(name : String) : Boolean
}