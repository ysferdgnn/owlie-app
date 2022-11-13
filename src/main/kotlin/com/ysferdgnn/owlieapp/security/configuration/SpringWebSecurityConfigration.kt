package com.ysferdgnn.owlieapp.security.configuration

import com.ysferdgnn.owlieapp.security.JwtAuthenticationEntryPoint
import com.ysferdgnn.owlieapp.security.JwtTokenFilter
import com.ysferdgnn.owlieapp.security.service.JwtUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter




class SpringWebSecurityConfigration(
    val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    val jwtUserDetailsService: JwtUserDetailsService,
    val jwtTokenFilter: JwtTokenFilter
) : WebSecurityConfigurerAdapter() {


    @Bean
    @org.springframework.context.annotation.Lazy
    override fun authenticationManagerBean()
            : AuthenticationManager? {
        return super.authenticationManagerBean();
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder();
    }


    override fun configure(httpSecurity: HttpSecurity) {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers("/api/v1/auth/**")
            .permitAll()
            .anyRequest()
            .authenticated()

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}