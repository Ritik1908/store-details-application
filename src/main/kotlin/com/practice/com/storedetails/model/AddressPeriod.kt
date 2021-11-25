package com.practice.com.storedetails.model

import java.time.LocalDate

data class AddressPeriod(
    var dateValidFrom: LocalDate,
    var dateValidUntil: LocalDate,
    var storeAddress: StoreAddress
)