package com.practice.com.storedetails.controller


import com.practice.com.storedetails.configuration.endpointUrl
import com.practice.com.storedetails.model.StoreDetails
import com.practice.com.storedetails.service.StoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Controller (val storeService: StoreService) {

    @GetMapping(endpointUrl)
    fun getStores(@RequestParam(name = "refDate", required = false) date: String?): List<StoreDetails> {
//        println(Configuration().endpointUrl)
//        println(endpointUrl)
        return storeService.getAll(date)
    }

    @GetMapping(endpointUrl+"{id}")
    fun getStore(@PathVariable id: Int): Optional<StoreDetails> {
        return storeService.getById(id)
    }

    @PostMapping(endpointUrl)
    fun saveStoreDetails(@RequestBody request: StoreDetails): String {
        return storeService.saveStore(request)
    }
}