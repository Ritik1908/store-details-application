package com.practice.com.storedetails.dao

import com.practice.com.storedetails.model.StoreDetails
import org.springframework.data.mongodb.repository.MongoRepository

interface StoreDetailsJPA: MongoRepository<StoreDetails, String> {
}