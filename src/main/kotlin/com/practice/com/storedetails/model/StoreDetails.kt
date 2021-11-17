package com.practice.com.storedetails.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Indexed
import java.util.*

@Document
data class StoreDetails(
    @Id
    var id: Int,
    var name: String,
    var status: String,
    var createdAt: Date,
    var updatedAt: Date,
    var addressPeriod: List<AddressPeriod>
)