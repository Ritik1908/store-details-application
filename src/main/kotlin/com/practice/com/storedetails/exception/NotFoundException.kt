package com.practice.com.storedetails.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class CustomExceptionMessage(message: String): RuntimeException(message)