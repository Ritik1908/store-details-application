package com.practice.com.storedetails.dao

import com.practice.com.storedetails.model.AddressPeriod
import org.springframework.data.jpa.repository.JpaRepository

interface AddressJpa: JpaRepository<AddressPeriod, Long> {
}