package com.ysferdgnn.owlieapp.service

import com.ysferdgnn.owlieapp.dto.request.AppUserLoginRequestDTO
import com.ysferdgnn.owlieapp.exception.UnAuthorizedException
import com.ysferdgnn.owlieapp.exception.UserBadCredentials
import com.ysferdgnn.owlieapp.exception.UserExistsError
import com.ysferdgnn.owlieapp.model.AppUser
import com.ysferdgnn.owlieapp.repository.AppUserRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.Unauthorized

@Service
class AppUserService (
    val appUserRepository: AppUserRepository
    )
{
    fun getAllAppUsers() : MutableList<AppUser>{
        return appUserRepository.findAll();
    }

    fun saveUser(appUser: AppUser) : AppUser {

        if(appUser.username==null || appUser.password== null){
            throw UserBadCredentials();
        }

        val appUserCheck =appUserRepository.findByUsername(appUser.username);
        if(appUserCheck !=null){
            throw  UserExistsError();
        }


        return appUserRepository.save(appUser);
    }

    fun login (appUserLoginRequestDTO: AppUserLoginRequestDTO) : AppUser{
        if (appUserLoginRequestDTO.username ==null || appUserLoginRequestDTO.password==null){
            throw UserBadCredentials();
        }
        return appUserRepository
            .findByUsernameAndPassword(appUserLoginRequestDTO.username,appUserLoginRequestDTO.password)
            ?: throw UnAuthorizedException()

    }
}