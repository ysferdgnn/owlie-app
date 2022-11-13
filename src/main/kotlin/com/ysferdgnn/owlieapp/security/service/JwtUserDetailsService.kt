package com.ysferdgnn.owlieapp.security.service

import com.ysferdgnn.owlieapp.security.model.JwtUserDetails
import com.ysferdgnn.owlieapp.service.AppUserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(val appUserService: AppUserService) :UserDetailsService{


    override fun loadUserByUsername(username: String?): UserDetails? {
        val appUser = appUserService.findByUsername(username)
        return JwtUserDetails.from(appUser)
    }
}