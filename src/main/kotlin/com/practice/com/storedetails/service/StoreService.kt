package com.practice.com.storedetails.service

import com.practice.com.storedetails.repository.StoreDetailsRepository
import com.practice.com.storedetails.exception.NotFoundException
import com.practice.com.storedetails.model.StoreDetails
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class StoreService (val storeDetailsRepository: StoreDetailsRepository) {

    fun getAll(date: String?): List<StoreDetails> {
        var data: List<StoreDetails> = mutableListOf()
        data = if(date != null) {
            val format = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.ENGLISH)
            val dateConverted = (LocalDate.parse(date.toString(), format))
            storeDetailsRepository.findByUpdatedAtGreaterThan(dateConverted)
        } else {
            storeDetailsRepository.findAll()
        }
        if(data.isEmpty()) {
            throw NotFoundException("No records present in database")
        }
        return data
    }

    fun getById(id: Int): Optional<StoreDetails> {
        val data = storeDetailsRepository.findById(id)

        if(data.isEmpty) {
            throw NotFoundException("No store found with id - $id")
        }
        return data
    }

    fun saveStore(storeDetails: StoreDetails): String {
        storeDetailsRepository.save(storeDetails)
        return "Details Saved"
    }
}