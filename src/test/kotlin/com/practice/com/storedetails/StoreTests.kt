package com.practice.com.storedetails

import com.practice.com.storedetails.controller.Controller
import com.practice.com.storedetails.model.AddressPeriod
import com.practice.com.storedetails.model.StoreAddress
import com.practice.com.storedetails.model.StoreDetails
import com.practice.com.storedetails.repository.StoreDetailsRepository
import com.practice.com.storedetails.service.ResponseMessage
import com.practice.com.storedetails.service.StoreService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.util.*


@RunWith(SpringRunner::class)
@WebMvcTest(value = [Controller::class])
class StoreTests {

    @Autowired
    lateinit var mockBean: MockMvc

    @MockBean
    lateinit var storeService: StoreService

    @MockBean
    lateinit var storeDetailsRepository: StoreDetailsRepository

    // To Test GET ALL STORES API
    @org.junit.Test
    fun testGetAllRequest() {
        val createdAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)
        val updatedAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)

        val storeAddress = StoreAddress("a", 1, "b", 1, "c", "d")
        val addressPeriod = listOf<AddressPeriod>(AddressPeriod(LocalDate.of(2021, 1,1), LocalDate.of(2022, 1,1), storeAddress))
        val storeDetails = listOf<StoreDetails>(StoreDetails(1, "store 1", "Active", createdAt, updatedAt, addressPeriod))

        Mockito.`when`(storeService.getAll(null, false)).thenReturn(storeDetails)

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders.get("/store-service/v1/stores/")

        val result: MvcResult = mockBean.perform(requestBuilder).andReturn()

        val expected =
            "[{'id':1, 'name': 'store 1', 'status': 'Active', 'createdAt': '$createdAt', 'lastUpdated': '$updatedAt', 'addressPeriod':[{'dateValidFrom': '2021-01-01', 'dateValidUntil': '2022-01-01', 'storeAddress':{'street': 'a', 'houseNumber':1, 'houseNumberSuffix': 'b', 'postalCode':1, 'city': 'c', 'country': 'd'}}]}]"

        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
    }

    // To Test GET ALL STORES with Date Valid Until Parameter
    @org.junit.Test
    fun testGetAllRequestValidUntil() {
        val createdAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)
        val updatedAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)

        val storeAddress = StoreAddress("a", 1, "b", 1, "c", "d")
        val addressPeriod = listOf<AddressPeriod>(AddressPeriod(LocalDate.of(2021, 1,1), LocalDate.of(2022, 1,1), storeAddress))
        val storeDetails = listOf<StoreDetails>(StoreDetails(1, "store 1", "Active", createdAt, updatedAt, addressPeriod))

        Mockito.`when`(storeService.getAll(LocalDate.of(2021, 1, 1), false)).thenReturn(storeDetails)

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders.get("/store-service/v1/stores/").param("refDate", "2021-01-01")

        val result: MvcResult = mockBean.perform(requestBuilder).andReturn()

        val expected =
            "[{'id':1, 'name': 'store 1', 'status': 'Active', 'createdAt': '$createdAt', 'lastUpdated': '$updatedAt', 'addressPeriod':[{'dateValidFrom': '2021-01-01', 'dateValidUntil': '2022-01-01', 'storeAddress':{'street': 'a', 'houseNumber':1, 'houseNumberSuffix': 'b', 'postalCode':1, 'city': 'c', 'country': 'd'}}]}]"

        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
    }

    // To Test GET ALL STORES with Date Valid Until Parameter and future flag as true
    @org.junit.Test
    fun testGetAllRequestValidUntilFutureFlag() {
        val createdAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)
        val updatedAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)

        val storeAddress = StoreAddress("a", 1, "b", 1, "c", "d")
        val addressPeriod = listOf<AddressPeriod>(AddressPeriod(LocalDate.of(2021, 1,1), LocalDate.of(2022, 1,1), storeAddress))
        val storeDetails = listOf<StoreDetails>(StoreDetails(1, "store 1", "Active", createdAt, updatedAt, addressPeriod))

        Mockito.`when`(storeService.getAll(LocalDate.of(2021, 1, 1), true)).thenReturn(storeDetails)

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders.get("/store-service/v1/stores/").param("refDate", "2021-01-01").param("futureFlag", "true")

        val result: MvcResult = mockBean.perform(requestBuilder).andReturn()

        val expected =
            "[{'id':1, 'name': 'store 1', 'status': 'Active', 'createdAt': '$createdAt', 'lastUpdated': '$updatedAt', 'addressPeriod':[{'dateValidFrom': '2021-01-01', 'dateValidUntil': '2022-01-01', 'storeAddress':{'street': 'a', 'houseNumber':1, 'houseNumberSuffix': 'b', 'postalCode':1, 'city': 'c', 'country': 'd'}}]}]"

        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
    }

    // To Test GET STORE BY ID
    @org.junit.Test
    fun testGetRequestById() {

        val createdAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)
        val updatedAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)

        val storeAddress = StoreAddress("a", 1, "b", 1, "c", "d")
        val addressPeriod = listOf<AddressPeriod>(AddressPeriod(LocalDate.of(2021, 1,1), LocalDate.of(2022, 1,1), storeAddress))
        val storeDetails = Optional.ofNullable(StoreDetails(1, "store 1", "Active", createdAt, updatedAt, addressPeriod))

        Mockito.`when`(storeDetailsRepository.findById(Mockito.anyInt())).thenReturn(storeDetails)
        Mockito.`when`(storeService.getById(1)).thenReturn(storeDetails)

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders.get("/store-service/v1/stores/1")

        val result: MvcResult = mockBean.perform(requestBuilder).andReturn()

        println(result.response.contentAsString)

        val expected =
            "{'id':1, 'name': 'store 1', 'status': 'Active', 'createdAt': '$createdAt', 'lastUpdated': '$updatedAt', 'addressPeriod':[{'dateValidFrom': '2021-01-01', 'dateValidUntil': '2022-01-01', 'storeAddress':{'street': 'a', 'houseNumber':1, 'houseNumberSuffix': 'b', 'postalCode':1, 'city': 'c', 'country': 'd'}}]}"

        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
    }

    // To Test Delete Request to save new store
    @Test
    fun testDeleteRequest() {
        val timestamp = Date()

        Mockito.`when`(storeService.deleteStore(Mockito.anyInt())).thenReturn(ResponseEntity(ResponseMessage(timestamp, "Deleted Successfully"), HttpStatus.CREATED))

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders
            .delete("/store-service/v1/stores/1")

        val result: MvcResult = mockBean.perform(requestBuilder).andReturn()

        val expected = "{'timestamp': '${timestamp.toString()}','message': 'Store Details Saved'}"

//        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
    }

    // To Test POST Request to save new store
    @Test
    fun testPostRequest() {

        val postRequestContent = "{'id': 1, 'name': 'Store 1', 'status': 'Active', 'createdAt': '2017-01-13T17:09:42.411', 'lastUpdated': '2017-01-13T17:09:42.411', 'addressPeriod': [{'dateValidFrom': '2021-01-01', 'dateValidUntil': '2022-01-01', 'storeAddress': { 'street': 'a', 'houseNumber': 10, 'houseNumberSuffix': 'a', 'postalCode': 10, 'city': 'abc', 'country': 'ab' } } ]}"

        val createdAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)
        val updatedAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)

        val storeAddress = StoreAddress("a", 1, "b", 1, "c", "d")
        val addressPeriod = listOf<AddressPeriod>(AddressPeriod(LocalDate.of(2021, 1,1), LocalDate.of(2022, 1,1), storeAddress))
        val storeDetails = StoreDetails(1, "store 1", "Active", createdAt, updatedAt, addressPeriod)

        val timestamp = Date()
        Mockito.`when`(storeDetailsRepository.save(Mockito.any(StoreDetails::class.java))).thenReturn(null)
        Mockito.`when`(storeDetailsRepository.existsById(Mockito.anyInt())).thenReturn(true)

//        Mockito.`when`(storeService.saveStore(storeDetails)).thenReturn(ResponseEntity(ResponseMessage(timestamp, "Store Details Saved"), HttpStatus.CREATED))

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders
            .post("/store-service/v1/stores/")
            .content(postRequestContent)
            .contentType(MediaType.APPLICATION_JSON)

        println(requestBuilder.toString())

        val result: MvcResult = mockBean.perform(requestBuilder).andReturn()

        println("k")
        println(result.response)
        println(result.response.contentAsString)

        val expected =
            "[{'id':1, 'name': 'store 1', 'status': 'Active', 'createdAt': '$createdAt', 'lastUpdated': '$updatedAt', 'addressPeriod':[{'dateValidFrom': '2021-01-01', 'dateValidUntil': '2022-01-01', 'storeAddress':{'street': 'a', 'houseNumber':1, 'houseNumberSuffix': 'b', 'postalCode':1, 'city': 'c', 'country': 'd'}}]}]"

        println(expected)

//        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
    }
}

