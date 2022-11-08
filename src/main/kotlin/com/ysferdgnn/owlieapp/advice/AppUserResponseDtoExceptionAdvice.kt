package com.ysferdgnn.owlieapp.advice

import com.ysferdgnn.owlieapp.exception.UnAuthorizedException
import com.ysferdgnn.owlieapp.exception.UserBadCredentials
import com.ysferdgnn.owlieapp.exception.UserExistsError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class AppUserResponseEntityExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler
    fun  handleError(userBadCredentials: UserBadCredentials) : ResponseEntity<Any?>{
        return ResponseEntity.badRequest().body(userBadCredentials.message?:"");
    }
    @ExceptionHandler
    fun  handleError(userExistsError: UserExistsError) : ResponseEntity<Any?>{
        return ResponseEntity(userExistsError.message?:"",HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler
    fun handleError(unAuthorizedException: UnAuthorizedException):ResponseEntity<Any?>{
        return  ResponseEntity(unAuthorizedException.message?:"",HttpStatus.UNAUTHORIZED);
    }
}