package com.practice.com.storedetails.model

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
@JsonFilter("StoreDetailsFilter")
data class StoreDetails(
    @Id
    var id: Int,
    var name: String,
    var status: String,
    var createdAt: Date,
    @JsonProperty("lastUpdated")
    var updatedAt: Date,
    var addressPeriod: List<AddressPeriod>
)