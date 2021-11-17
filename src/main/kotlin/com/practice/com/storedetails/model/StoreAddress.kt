package com.practice.com.storedetails.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class StoreAddress(
    var street: String,
    var houseNumber: Int,
    var houseNumberSuffix: String,
    var postalCode: Int,
    var city: String,
    var country: String
)