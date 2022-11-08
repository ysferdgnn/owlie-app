package com.ysferdgnn.owlieapp.controller

import com.ysferdgnn.owlieapp.dto.request.AppUserLoginRequestDTO
import com.ysferdgnn.owlieapp.dto.request.AppUserRegisterRequestDTO
import com.ysferdgnn.owlieapp.dto.response.AppUserLoginResponseDTO
import com.ysferdgnn.owlieapp.dto.response.AppUserRegisterResponseDTO
import com.ysferdgnn.owlieapp.model.AppUser
import com.ysferdgnn.owlieapp.service.AppUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/app-user")
class AppUserController(
   val appUserService: AppUserService
) {



    @GetMapping
    fun getAppUsers() : MutableList<AppUser>{
        return appUserService.getAllAppUsers();
    }

    @PostMapping
    fun register(@RequestBody appUserRequestDTO: AppUserRegisterRequestDTO) :ResponseEntity<AppUserRegisterResponseDTO>{
        val appUser = appUserService.saveUser(AppUser.from(appUserRequestDTO));
        val appUserResponseDTO =  AppUserRegisterResponseDTO.from(appUser);
        return ResponseEntity.ok().body(appUserResponseDTO);
    }

    @PostMapping("/login")
    fun login (@RequestBody appUserLoginDTO: AppUserLoginRequestDTO) : ResponseEntity<AppUserLoginResponseDTO>{
        val appUser = appUserService.login(appUserLoginDTO);
        return  ResponseEntity.ok().body(AppUserLoginResponseDTO.from(appUser));

    }



}