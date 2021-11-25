package com.practice.com.storedetails.controller


import com.practice.com.storedetails.model.StoreDetails
import com.practice.com.storedetails.service.StoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Controller (val storeService: StoreService) {

//    @Autowired
//    @Value("\${store.service.url}")
//    lateinit var url: String

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
}