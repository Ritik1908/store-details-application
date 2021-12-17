package com.practice.com.storedetails.controller

import com.practice.com.storedetails.configuration.endpointId
import com.practice.com.storedetails.configuration.endpointUrl
import com.practice.com.storedetails.model.StoreDetails
import com.practice.com.storedetails.service.ResponseMessage
import com.practice.com.storedetails.service.StoreService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.awt.print.Book
import java.time.LocalDate
import java.util.*
import javax.validation.Valid

@RestController
class Controller (val storeService: StoreService) {

    @Operation(summary = "Get all Stores, if future flag is false return only current records. Default value for refDate is current date.")
    @ApiResponses(
        value = [
            ApiResponse (
                responseCode = "200",
                description = "Found all valid Stores",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = StoreDetails::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "No records present in database",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ResponseMessage::class))]
            )
        ]
    )
    @GetMapping(endpointUrl)
    fun getStores(
        @RequestParam(name = "refDate", required = false) date: LocalDate? = LocalDate.now(),
        @RequestParam(name="futureFlag", required = false) futureFlag: Boolean = false
    ): List<StoreDetails> {
        return storeService.getAll(date, futureFlag)
    }

    @Operation(summary = "Get store by id.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Returns store with a given id",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = StoreDetails::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "No store found with id",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ResponseMessage::class))]
            )
        ]
    )
    @GetMapping(endpointId)
    fun getStore(@PathVariable id: Int): Optional<StoreDetails> {
        return storeService.getById(id)
    }

    @Operation(summary = "Add a new store.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Saves store to database",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ResponseMessage::class))]
            ),
            ApiResponse(
                responseCode = "406",
                description = "Store Already Exists with given id",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ResponseMessage::class))]
            )
        ]
    )
    @PostMapping(endpointUrl)
    fun saveStoreDetails(@Valid @RequestBody request: StoreDetails): ResponseEntity<ResponseMessage> {
        return storeService.saveStore(request)
    }

    @Operation(summary = "Update store details.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Updates store, if already not present creates a new one.",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ResponseMessage::class))]
            )
        ]
    )
    @PutMapping(endpointUrl)
    fun updateStoreDetails(@RequestBody request: StoreDetails): ResponseEntity<ResponseMessage> {
        return storeService.updateStore(request)
    }

    @Operation(summary = "Deletes a store by id.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Deletes a store from the database.",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ResponseMessage::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "No store found with given id.",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ResponseMessage::class))]
            )
        ]
    )
    @DeleteMapping(endpointId)
    fun deleteStoreDetails(@PathVariable id: Int): ResponseEntity<ResponseMessage> {
        return storeService.deleteStore(id)
    }
}