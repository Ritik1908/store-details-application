package com.practice.com.storedetails.service

import com.practice.com.storedetails.exception.CustomExceptionMessage
import com.practice.com.storedetails.repository.StoreDetailsRepository
import com.practice.com.storedetails.model.StoreDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
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
                store.addressPeriod.filter { validity ->  validity.dateValidUntil == null ||  validity.dateValidUntil!! >= refDate }
        }
        return output
    }

    fun getAll(date: LocalDate?, futureFlag: Boolean): List<StoreDetails> {

        val refDate = date ?: LocalDate.now()

        var output: List<StoreDetails> = storeDetailsRepository
            .findByAddressPeriodDateValidUntilGreaterThanOrAddressPeriodDateValidUntilNull(refDate)
            .ifEmpty {
                throw CustomExceptionMessage("No records present in database")
            }

        output = if (!futureFlag) {
            filterCurrentRecords(output, refDate)
        } else {
            filterCurrentAndFutureRecords(output, refDate)
        }

        output = output
            .filter { store -> store.addressPeriod.isNotEmpty() }
            .ifEmpty { throw CustomExceptionMessage("No records present in database") }

        return output
    }

    fun getById(id: Int): Optional<StoreDetails> {
        val data = storeDetailsRepository.findById(id)
        if(data.isEmpty) {
            throw CustomExceptionMessage("No store found with id - $id")
        }
        return data
    }

    fun saveStore(storeDetails: StoreDetails): ResponseEntity<ResponseMessage> {
        return if(storeDetailsRepository.existsById(storeDetails.id)) {
            ResponseEntity(ResponseMessage(message = "Store Already Exists with given id"), HttpStatus.NOT_ACCEPTABLE)
        } else {
            storeDetailsRepository.save(storeDetails)
            ResponseEntity(ResponseMessage(message = "Store Details Saved"), HttpStatus.CREATED)
        }
    }

    fun updateStore(storeDetails: StoreDetails): ResponseEntity<ResponseMessage> {
        return if(storeDetailsRepository.existsById(storeDetails.id)) {
            storeDetails.updatedAt = LocalDateTime.now()
            storeDetailsRepository.save(storeDetails)
            ResponseEntity(ResponseMessage(message = "Update Successful"), HttpStatus.OK)
        } else {
            storeDetailsRepository.save(storeDetails)
            ResponseEntity(ResponseMessage(message = "No store exists with given id, hence created new one"), HttpStatus.OK)
        }
    }

    fun deleteStore(id: Int): ResponseEntity<ResponseMessage> {
        return if(storeDetailsRepository.existsById(id)) {
            storeDetailsRepository.deleteById(id)
            ResponseEntity(ResponseMessage(message = "Deleted Successfully"), HttpStatus.OK)
        } else {
            ResponseEntity(ResponseMessage(message = "No store exists with the given id to delete"), HttpStatus.NOT_FOUND)
        }
    }
}