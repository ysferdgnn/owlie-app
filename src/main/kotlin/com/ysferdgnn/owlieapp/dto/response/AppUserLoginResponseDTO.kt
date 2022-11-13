package com.ysferdgnn.owlieapp.dto.response

import com.ysferdgnn.owlieapp.model.AppUser

data class AppUserLoginResponseDTO (
    //TODO: change username and password as jwt token
    val username: String?,
    val token: String?
        )
{
    companion object Factory{
        fun from(appUser: AppUser) :AppUserLoginResponseDTO{
            return AppUserLoginResponseDTO(username = appUser.username, token = null)
        }
        fun fromWithToken(appUser: AppUser?,token: String):AppUserLoginResponseDTO{
            return  AppUserLoginResponseDTO(username = appUser?.username,token=token)
        }
    }
}