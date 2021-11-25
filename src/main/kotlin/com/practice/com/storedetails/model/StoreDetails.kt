package com.practice.com.storedetails.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime

@Document("store-master")
data class StoreDetails(
    @Id
    val id: Int,
    var name: String,
    var status: String,
    val createdAt: LocalDateTime,
    @JsonProperty("lastUpdated")
    var updatedAt: LocalDateTime,
    var addressPeriod: List<AddressPeriod>
)