package com.practice.com.storedetails.model

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document
@JsonFilter("StoreDetailsFilter")
data class StoreDetails(
    @Id
    var id: Int,
    var name: String,
    var status: String,
    var createdAt: LocalDate,
    @JsonProperty("lastUpdated")
    var updatedAt: LocalDate,
    var addressPeriod: List<AddressPeriod>
)