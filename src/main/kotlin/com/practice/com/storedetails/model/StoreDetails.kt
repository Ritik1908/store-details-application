package com.practice.com.storedetails.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

@Document("store-master")
data class StoreDetails(
    @Id
    val id: Int,
    var name: String,
    @NotNull
    var status: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonProperty("lastUpdated")
    var updatedAt: LocalDateTime?,
    var addressPeriod: List<AddressPeriod>
)