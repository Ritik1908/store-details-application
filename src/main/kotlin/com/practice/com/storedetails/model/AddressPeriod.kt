package com.practice.com.storedetails.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

data class AddressPeriod(
    var dateValidFrom: Date,
    var dateValidUntil: Date,
    var storeAddress: StoreAddress
)