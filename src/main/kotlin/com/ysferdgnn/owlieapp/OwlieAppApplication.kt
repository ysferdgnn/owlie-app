package com.ysferdgnn.owlieapp

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebSecurity

class OwlieAppApplication

fun main(args: Array<String>) {
	println("şifreeee"+Keys.secretKeyFor(SignatureAlgorithm.HS512))
	runApplication<OwlieAppApplication>(*args)

}
