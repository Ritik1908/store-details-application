package com.practice.com.storedetails.model

import java.time.LocalDate
import java.util.*

data class AddressPeriod(
    var dateValidFrom: LocalDate,
    var dateValidUntil: LocalDate,
    var storeAddress: StoreAddress
)