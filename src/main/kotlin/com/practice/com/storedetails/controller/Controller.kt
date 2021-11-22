package com.practice.com.storedetails.controller

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.practice.com.storedetails.dao.StoreDetailsRepository
import com.practice.com.storedetails.exception.NotFoundException
import com.practice.com.storedetails.model.StoreDetails
import com.practice.com.storedetails.service.StoreService
import org.springframework.http.converter.json.MappingJacksonValue
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RestController
class Controller (val storeService: StoreService) {

    @GetMapping("/store-service/v1/stores")
    fun getStores(@RequestParam(name = "refDate", required = false) date: String?): List<StoreDetails> {
        return storeService.getAll(date)
    }

    @GetMapping("/store-service/v1/stores/{id}")
    fun getStore(@PathVariable id: Int): Optional<StoreDetails> {
        return storeService.getById(id)
    }

    @PostMapping("/store-service/v1/stores")
    fun saveStoreDetails(@RequestBody request: StoreDetails): String {
        return storeService.saveStore(request)
    }
//

}