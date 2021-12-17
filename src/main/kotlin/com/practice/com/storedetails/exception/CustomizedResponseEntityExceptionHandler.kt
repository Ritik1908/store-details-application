package com.practice.com.storedetails.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*


@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleAllException(ex: Exception, request: WebRequest): ResponseEntity<Any>? {
        val exceptionResponse = ExceptionResponse(Date(), ex.message, request.getDescription(false))
        return ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(CustomExceptionMessage::class)
    fun handleNotFoundException(ex: CustomExceptionMessage, request: WebRequest): ResponseEntity<Any>? {
        val exceptionResponse = ExceptionResponse(Date(),  ex.message, request.getDescription(false))
        return ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleException(ex: MethodArgumentTypeMismatchException, request: WebRequest): ResponseEntity<Any>? {
        val exceptionResponse = ExceptionResponse(Date(),  "Illegal arguments passed in request parameter", request.getDescription(false))
        return ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    protected fun handleHttpMessageNotReadableException(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<Any?>? {
    println("Ok")
        val exceptionResponse = ExceptionResponse(Date(),  "Illegal arguments passed in request parameter", request.getDescription(false))
        return ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND)
    }

}