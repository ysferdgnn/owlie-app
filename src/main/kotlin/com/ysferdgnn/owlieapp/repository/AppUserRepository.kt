package com.ysferdgnn.owlieapp.repository

import com.ysferdgnn.owlieapp.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository

interface AppUserRepository : JpaRepository<AppUser,String> {
    fun findByUsername(username : String?) : AppUser?
    fun findByUsernameAndPassword(username: String,password:String) : AppUser?
}