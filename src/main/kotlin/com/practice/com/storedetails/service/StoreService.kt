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

    fun filterCurrentRecords(output: List<StoreDetails>, refDate: LocalDate): List<StoreDetails> {
        output.forEach { store ->
            store.addressPeriod =
                store.addressPeriod.filter { validity ->  validity.dateValidFrom <= refDate && (validity.dateValidUntil == null ||  validity.dateValidUntil!! >= refDate) }
        }
        return output
    }

    fun filterCurrentAndFutureRecords(output: List<StoreDetails>, refDate: LocalDate): List<StoreDetails> {
        output.forEach { store ->
            store.addressPeriod =
                store.addressPeriod.filter { validity ->  (validity.dateValidFrom <= refDate && (validity.dateValidUntil == null ||  validity.dateValidUntil!! >= refDate)) || validity.dateValidFrom >= refDate }
        }
        return output
    }

    fun getAll(date: LocalDate?, futureFlag: Boolean = false): List<StoreDetails> {

        var output: List<StoreDetails> = storeDetailsRepository.findAll().ifEmpty { throw NotFoundException("No records present in database") }

        if(date != null) {
            output = if (!futureFlag) {
                filterCurrentRecords(output, date)
            } else {
                filterCurrentAndFutureRecords(output, date)
            }
        }
        output = output.filter{ store -> store.addressPeriod.isNotEmpty()}.ifEmpty { throw NotFoundException("No records present in database") }
        return output
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