package com.practice.com.storedetails.controller

import com.practice.com.storedetails.dao.AddressJpa
import com.practice.com.storedetails.dao.StoreDetailsJPA
import com.practice.com.storedetails.model.StoreDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Controller @Autowired constructor(val addressJpa: AddressJpa, val storeDetails: StoreDetailsJPA) {


    @GetMapping("/store-service/v1/stores")
    fun getStores(): MutableList<StoreDetails> {
        return storeDetails.findAll()
    }

    @GetMapping("/store-service/v1/stores/{id}")
    fun getStore(@PathVariable id: Long): Optional<StoreDetails> {
        return storeDetails.findById(id)
    }

    @PostMapping("/store-service/v1/stores")
    fun saveStoreDetails(@RequestBody request: StoreDetails): String {
        storeDetails.save(request)
        return "Details Saved"
    }


}