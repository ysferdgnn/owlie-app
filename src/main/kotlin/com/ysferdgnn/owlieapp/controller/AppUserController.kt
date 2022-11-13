package com.ysferdgnn.owlieapp.controller

import com.ysferdgnn.owlieapp.dto.request.AppUserLoginRequestDTO
import com.ysferdgnn.owlieapp.dto.request.AppUserRegisterRequestDTO
import com.ysferdgnn.owlieapp.dto.response.AppUserLoginResponseDTO
import com.ysferdgnn.owlieapp.dto.response.AppUserRegisterResponseDTO
import com.ysferdgnn.owlieapp.model.AppUser
import com.ysferdgnn.owlieapp.security.JwtTokenProvider
import com.ysferdgnn.owlieapp.service.AppUserService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AppUserController(
    val appUserService: AppUserService,
    val authenticationManager: AuthenticationManager,
    val jwtTokenProvider: JwtTokenProvider,
    val passwordEncoder: BCryptPasswordEncoder
) {


    @GetMapping
    fun getAppUsers(): MutableList<AppUser> {
        return appUserService.getAllAppUsers();
    }

    @PostMapping("/register")
    fun register(@RequestBody appUserRequestDTO: AppUserRegisterRequestDTO): ResponseEntity<AppUserRegisterResponseDTO> {

        val appUserRequestDTOSave = AppUserLoginRequestDTO(
            username = appUserRequestDTO.username,
            password = passwordEncoder.encode(appUserRequestDTO.password)
        )
        val appUser = appUserService.saveUser(AppUser.from(appUserRequestDTOSave));
        val appUserResponseDTO = AppUserRegisterResponseDTO.from(appUser);
        return ResponseEntity.ok().body(appUserResponseDTO);
    }

    @PostMapping("/login")
    fun login(@RequestBody appUserLoginDTO: AppUserLoginRequestDTO): ResponseEntity<AppUserLoginResponseDTO> {

        val usernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken(appUserLoginDTO.username, appUserLoginDTO.password)
        val authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
        val appUser= appUserService.findByUsername(appUserLoginDTO.username)
        SecurityContextHolder.getContext().authentication =authentication

        val jwtToken ="Bearer" +jwtTokenProvider.generateToken(authentication)


        return ResponseEntity.ok().body(AppUserLoginResponseDTO.fromWithToken(appUser=appUser, token = jwtToken));

    }


}