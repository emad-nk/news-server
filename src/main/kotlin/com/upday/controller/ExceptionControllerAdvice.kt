package com.upday.controller

import com.upday.exception.ConstraintsViolationException
import com.upday.exception.EntityNotFoundException
import org.hibernate.HibernateException
import org.modelmapper.MappingException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.time.LocalDateTime

/**
 * Puts exceptions into proper format for clients
 * in order to not show critical information
 */
@ControllerAdvice
class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException::class)
    fun entityNotFound(ex: EntityNotFoundException): ResponseEntity<ApiError> {
        val errorDetails = ApiError(LocalDateTime.now(),
            "Entity not found.",
            ex.message)
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ResponseBody
    @ExceptionHandler(ConstraintsViolationException::class)
    fun constraintsViolation(ex: ConstraintsViolationException): ResponseEntity<ApiError> {
        val errorDetails = ApiError(LocalDateTime.now(),
            "Some constraints are violated.",
            ex.message)
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ResponseBody
    @ExceptionHandler(HibernateException::class)
    fun hibernateException(ex: HibernateException): ResponseEntity<ApiError> {
        val errorDetails = ApiError(LocalDateTime.now(),
            "Hibernate exception.",
            "No details")
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ResponseBody
    @ExceptionHandler(MappingException::class)
    fun mappingException(ex: MappingException): ResponseEntity<ApiError> {
        val errorDetails = ApiError(LocalDateTime.now(),
            "Could not map provided JSON.",
            "${ex.message!!}.  ${ex.cause}")
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun fieldValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiError> {
        val errorDetails = ApiError(LocalDateTime.now(),
            "Field validation error.",
            ex.message!!)

        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    data class ApiError(val time: LocalDateTime, val message: String, val details: String)

}
