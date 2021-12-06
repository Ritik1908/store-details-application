package com.practice.com.storedetails

import com.practice.com.storedetails.controller.Controller
import com.practice.com.storedetails.model.AddressPeriod
import com.practice.com.storedetails.model.StoreAddress
import com.practice.com.storedetails.model.StoreDetails
import com.practice.com.storedetails.repository.StoreDetailsRepository
import com.practice.com.storedetails.service.StoreService
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.skyscreamer.jsonassert.JSONAssert
//import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
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
//@SpringBootTest
@WebMvcTest(value = [Controller::class])
class StoreDetailsApplicationTests {

	@Autowired
	lateinit var mockBean: MockMvc

	@MockBean
	lateinit var storeService: StoreService

	@MockBean
	lateinit var storeDetailsRepository: StoreDetailsRepository

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

		println(result.response.contentAsString)

		val expected =
			"[{'id':1, 'name': 'store 1', 'status': 'Active', 'createdAt': '$createdAt', 'lastUpdated': '$updatedAt', 'addressPeriod':[{'dateValidFrom': '2021-01-01', 'dateValidUntil': '2022-01-01', 'storeAddress':{'street': 'a', 'houseNumber':1, 'houseNumberSuffix': 'b', 'postalCode':1, 'city': 'c', 'country': 'd'}}]}]"
		println(expected)

		JSONAssert.assertEquals(expected, result.response.contentAsString, false)
	}

	@org.junit.Test
	fun testGetRequest() {

		val createdAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)
		val updatedAt = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40)

		val storeAddress = StoreAddress("a", 1, "b", 1, "c", "d")
		val addressPeriod = listOf<AddressPeriod>(AddressPeriod(LocalDate.of(2021, 1,1), LocalDate.of(2022, 1,1), storeAddress))
		val storeDetails = Optional.ofNullable(StoreDetails(1, "store 1", "Active", createdAt, updatedAt, addressPeriod))

		Mockito.`when`(storeService.getById(1)).thenReturn(storeDetails)

		val requestBuilder: RequestBuilder = MockMvcRequestBuilders.get("/store-service/v1/stores/1")

		val result: MvcResult = mockBean.perform(requestBuilder).andReturn()

		val expected =
			"{'id':1, 'name': 'store 1', 'status': 'Active', 'createdAt': '$createdAt', 'lastUpdated': '$updatedAt', 'addressPeriod':[{'dateValidFrom': '2021-01-01', 'dateValidUntil': '2022-01-01', 'storeAddress':{'street': 'a', 'houseNumber':1, 'houseNumberSuffix': 'b', 'postalCode':1, 'city': 'c', 'country': 'd'}}]}"

		JSONAssert.assertEquals(expected, result.response.contentAsString, false)
	}

}

