package com.ysferdgnn.owlieapp.model

import com.ysferdgnn.owlieapp.dto.request.AppUserLoginRequestDTO
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class AppUser(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = null,

    val username: String,
    val password: String,
    val isNonLocked: Boolean,
    val isNonExpired: Boolean,
    val isCredentialsNonExpired: Boolean,
    val isEnabled: Boolean
) {
    companion object Factory {
        fun from(appUserRequestDTO: AppUserLoginRequestDTO): AppUser {
            return AppUser(
                username = appUserRequestDTO.username,
                password = appUserRequestDTO.password,
                isEnabled = true,
                isNonExpired = true,
                isNonLocked = true,
                isCredentialsNonExpired = true
            );
        }
    }
}




