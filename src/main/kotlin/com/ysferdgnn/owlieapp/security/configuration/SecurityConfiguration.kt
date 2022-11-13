package com.ysferdgnn.owlieapp.security.configuration

import com.ysferdgnn.owlieapp.security.JwtAuthenticationEntryPoint
import com.ysferdgnn.owlieapp.security.JwtTokenFilter
import com.ysferdgnn.owlieapp.security.service.JwtUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class SecurityConfiguration(
    val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    val jwtTokenFilter: JwtTokenFilter,
    val jwtUserDetailsService: JwtUserDetailsService
) {
    @Bean
    fun filterChain (httpSecurity: HttpSecurity) :SecurityFilterChain{
        httpSecurity
            .cors()
            .and()
            .csrf()
            .disable()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/v1/auth/**")
            .permitAll()
            .anyRequest()
            .authenticated()

        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        return httpSecurity.build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder();
    }

    @Bean
    fun authenticationManager(httpSecurity: HttpSecurity):AuthenticationManager{
        val authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.userDetailsService(jwtUserDetailsService)
            .passwordEncoder(passwordEncoder())
            return  authenticationManagerBuilder.build()
    }


//    @Bean
//    fun webSecurityCustomizer(): WebSecurityCustomizer? {
//        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().antMatchers("") }
//    }
}