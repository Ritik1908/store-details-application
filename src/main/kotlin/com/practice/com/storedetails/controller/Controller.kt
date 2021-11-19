package com.practice.com.storedetails.controller

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.practice.com.storedetails.dao.StoreDetailsJPA
import com.practice.com.storedetails.exception.NotFoundException
import com.practice.com.storedetails.model.StoreDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.json.MappingJacksonValue
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RestController
class Controller @Autowired constructor(val storeDetailsJPA: StoreDetailsJPA) {

    @GetMapping("/store-service/v1/stores")
    fun getStores(@RequestParam(name = "refDate", required = false) date: String?): MappingJacksonValue {

        var data: List<StoreDetails> = mutableListOf()

        if(date != null) {
            val format = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.ENGLISH)
            val dateConverted = (LocalDate.parse(date.toString(), format))
            data = storeDetailsJPA.findByUpdatedAtGreaterThan(dateConverted)
        } else {
            data = storeDetailsJPA.findAll()
        }

        if(data.isEmpty()) {
            throw NotFoundException("No records present in database")
        }
        val filter = SimpleBeanPropertyFilter.serializeAllExcept("createdAt")
        val filtered = SimpleFilterProvider().addFilter("StoreDetailsFilter", filter)
        var mapping = MappingJacksonValue(data)
        mapping.filters = filtered

        return mapping
    }

    @GetMapping("/store-service/v1/stores/{id}")
    fun getStore(@PathVariable id: Int): MappingJacksonValue {
        val data = storeDetailsJPA.findById(id)
        if(data.isEmpty()) {
            throw NotFoundException("No store found with given id")
        }

        val filter = SimpleBeanPropertyFilter.serializeAllExcept("createdAt")
        val filtered = SimpleFilterProvider().addFilter("StoreDetailsFilter", filter)
        var mapping = MappingJacksonValue(data)
        mapping.filters = filtered

        return mapping
    }

    @GetMapping("/store-service/v1/stores/custom")
    fun getDate(): MappingJacksonValue {
        val data = storeDetailsJPA.findByUpdatedAtGreaterThan(LocalDate.of(2021,5,5))
        if(data.isEmpty()) {
            throw NotFoundException("No store found with given id")
        }

        val filter = SimpleBeanPropertyFilter.serializeAllExcept("createdAt")
        val filtered = SimpleFilterProvider().addFilter("StoreDetailsFilter", filter)
        var mapping = MappingJacksonValue(data)
        mapping.filters = filtered

        return mapping
    }

    @PostMapping("/store-service/v1/stores")
    fun saveStoreDetails(@RequestBody request: StoreDetails): String {
        storeDetailsJPA.save(request)
        return "Details Saved"
    }
//

}