package com.practice.com.storedetails

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PracticeTaskApplication

fun main(args: Array<String>) {
	runApplication<PracticeTaskApplication>(*args)
	println("Running Project Task")
}
