package com.ysferdgnn.owlieapp.model

import com.ysferdgnn.owlieapp.dto.request.AppUserRegisterRequestDTO
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class AppUser (

             @Id
             @GeneratedValue(generator = "UUID")
             @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
             val id : String? =null,

             val username:String?=null,
             val password:String?=null
        ){
    companion object Factory{
        fun from(appUserRequestDTO: AppUserRegisterRequestDTO) : AppUser {
            return AppUser(username = appUserRequestDTO.username, password = appUserRequestDTO.password);
        }
    }
}




