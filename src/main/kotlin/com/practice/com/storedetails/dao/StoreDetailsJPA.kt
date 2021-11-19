package com.practice.com.storedetails.dao

import com.practice.com.storedetails.model.StoreDetails
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface StoreDetailsJPA: MongoRepository<StoreDetails, Int> {

    fun findByUpdatedAtGreaterThan(date: LocalDate): List<StoreDetails>

}