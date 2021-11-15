package com.practice.com.storedetails.dao

import com.practice.com.storedetails.model.StoreDetails
import org.springframework.data.jpa.repository.JpaRepository

interface StoreDetailsJPA: JpaRepository<StoreDetails, Long> {
}