package com.ysferdgnn.owlieapp.security

import com.ysferdgnn.owlieapp.security.model.JwtUserDetails
import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {
    @Value("\${jwt.appSecret}")
    private val appSecret: String = ""

    @Value("\${jwt.expiresIn}")
    private val jwtExpiresIn: Long = 0L

    @Value("\${jwt.refreshTokenExpiresIn}")
    private val jwtRefreshTokenExpiresIn: Long = 0L


    fun generateToken(authentication: Authentication): String? {
        val userDetails: JwtUserDetails = authentication.principal as JwtUserDetails;
        val expireDate = Date(Date().time + jwtExpiresIn);
        return Jwts.builder().setSubject(userDetails.username).setIssuedAt(Date())
            .setExpiration(expireDate).signWith(SignatureAlgorithm.HS512, appSecret).compact();
    }

    fun getUsernameFromJwt (token : String) : String?{
        val claims = Jwts.parser().setSigningKey(appSecret).parseClaimsJws(token).body as Claims;
        return claims.subject;
    }

    public fun validateToken(token:String) : Boolean{
        return try{
            Jwts.parser().setSigningKey(appSecret).parseClaimsJws(token)
            !isTokenExpired(token)
        }catch (e : Exception ){
            when(e){
                is SignatureException,
                is MalformedJwtException,
                is ExpiredJwtException,
                is UnsupportedJwtException,
                is IllegalArgumentException ->{
                    false
                }
                else -> throw  e;
            }
        }
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration: Date = Jwts.parser().setSigningKey(appSecret).parseClaimsJws(token).body.expiration
        return expiration.before(Date())
    }
}