package com.practice.com.storedetails.service

import com.practice.com.storedetails.exception.CustomExceptionMessage
import com.practice.com.storedetails.repository.StoreDetailsRepository
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

        var output: List<StoreDetails> = storeDetailsRepository.findAll().ifEmpty { throw CustomExceptionMessage("No records present in database") }

        if(date != null) {
            output = if (!futureFlag) {
                filterCurrentRecords(output, date)
            } else {
                filterCurrentAndFutureRecords(output, date)
            }
        }

        output = output.filter{ store -> store.addressPeriod.isNotEmpty()}.ifEmpty { throw CustomExceptionMessage("No records present in database") }
        return output
    }

    fun getById(id: Int): Optional<StoreDetails> {
        val data = storeDetailsRepository.findById(id)
        if(data.isEmpty) {
            throw CustomExceptionMessage("No store found with id - $id")
        }
        return data
    }

    fun saveStore(storeDetails: StoreDetails): String {
        return if(storeDetailsRepository.existsById(storeDetails.id)) {
            "Store already exists with given id."
        } else {
            storeDetailsRepository.save(storeDetails)
            "Details Saved"
        }
    }

    fun updateStore(storeDetails: StoreDetails): String {
        return if(storeDetailsRepository.existsById(storeDetails.id)) {
            storeDetailsRepository.save(storeDetails)
            "Update Successful"
        } else {
            storeDetailsRepository.save(storeDetails)
            "No store exists with given id, hence created new one"
        }
    }

    fun deleteStore(id: Int): String {
        return if(storeDetailsRepository.existsById(id)) {
            storeDetailsRepository.deleteById(id)
            "Deleted Successfully"
        } else {
            "No store exists with the given id to delete"
        }
    }
}