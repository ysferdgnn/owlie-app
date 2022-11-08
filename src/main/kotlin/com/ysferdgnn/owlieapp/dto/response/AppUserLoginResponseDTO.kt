package com.ysferdgnn.owlieapp.dto.response

import com.ysferdgnn.owlieapp.model.AppUser

data class AppUserLoginResponseDTO (
    //TODO: change username and password as jwt token
    val username: String?,
    val password: String?
        )
{
    companion object Factory{
        fun from(appUser: AppUser) :AppUserLoginResponseDTO{
            return AppUserLoginResponseDTO(username = appUser.username, password = appUser.password)
        }
    }
}