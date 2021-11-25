package com.practice.com.storedetails.model

data class StoreAddress(
    var street: String,
    var houseNumber: Int,
    var houseNumberSuffix: String,
    var postalCode: Int,
    var city: String,
    var country: String
)