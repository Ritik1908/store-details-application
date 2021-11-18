package com.practice.com.storedetails.model

import java.util.*

data class AddressPeriod(
    var dateValidFrom: Date,
    var dateValidUntil: Date,
    var storeAddress: StoreAddress
)