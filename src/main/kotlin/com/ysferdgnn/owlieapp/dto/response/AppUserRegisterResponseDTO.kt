package com.ysferdgnn.owlieapp.dto.response

import com.ysferdgnn.owlieapp.model.AppUser

data class AppUserRegisterResponseDTO(
    val username: String?,
    val password : String?
){
    companion object Factory {
        fun from (appUser: AppUser): AppUserRegisterResponseDTO {
            return AppUserRegisterResponseDTO(username = appUser.username, password = appUser.password);
        }
    }
}
