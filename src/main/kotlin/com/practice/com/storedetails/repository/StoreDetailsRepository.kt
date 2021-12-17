package com.practice.com.storedetails.repository

import com.practice.com.storedetails.model.StoreDetails
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface StoreDetailsRepository: MongoRepository<StoreDetails, Int> {
    fun findByAddressPeriodDateValidUntilGreaterThanOrAddressPeriodDateValidUntilNull(refDate: LocalDate?): MutableList<StoreDetails>
}