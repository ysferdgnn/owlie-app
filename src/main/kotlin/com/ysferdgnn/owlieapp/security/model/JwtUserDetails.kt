package com.ysferdgnn.owlieapp.security.model

import com.ysferdgnn.owlieapp.model.AppUser
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails(
    val appUsername:String,
    val appPassword:String,
    ) : UserDetails {




    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val grantedAuthorities = mutableListOf<GrantedAuthority>()
        grantedAuthorities.add(SimpleGrantedAuthority("user"))
        return grantedAuthorities

    }

    companion object Factory{
        fun from(appUser: AppUser?) : JwtUserDetails? {
           val grantedAuthorities = mutableListOf<GrantedAuthority>()
           grantedAuthorities.add(SimpleGrantedAuthority("user"))
            if(appUser==null){
                return null;
            }
             return JwtUserDetails(appUsername = appUser.username, appPassword = appUser.password)
        }
    }

    override fun getPassword(): String {
       return appPassword
    }

    override fun getUsername(): String {
        return  appUsername
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return  true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return  true
    }

}