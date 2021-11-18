package com.practice.com.storedetails

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class StoreDetailsApplication

fun main(args: Array<String>) {
	runApplication<StoreDetailsApplication>(*args)
	println("Running Project Task")
}
