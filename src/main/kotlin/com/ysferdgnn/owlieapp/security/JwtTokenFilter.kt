package com.ysferdgnn.owlieapp.security

import com.ysferdgnn.owlieapp.security.service.JwtUserDetailsService
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtUserDetailsService: JwtUserDetailsService
) : OncePerRequestFilter() {


    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        try {
            val token = extractJwtTokenFromRequest(request) ?: return

            if (!jwtTokenProvider.validateToken(token)) {
                return
            }
            val username = jwtTokenProvider.getUsernameFromJwt(token)
            val jwtUserDetails = jwtUserDetailsService.loadUserByUsername(username)
                ?: throw UsernameNotFoundException("user can not found!") //TODO: move message to property file


            val usernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.authorities)
            usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken


        } catch (exception: Exception) {
            throw  AuthenticationCredentialsNotFoundException("An Error Occurs")
        } finally {
            filterChain.doFilter(request, response)
        }


    }

    private fun extractJwtTokenFromRequest(request: HttpServletRequest): String? {
        val bearer = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            bearer.substring("Bearer".length + 1)
        } else null
    }
}