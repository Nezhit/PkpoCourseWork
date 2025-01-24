package com.coursework.mainservice

import com.coursework.mainservice.dto.ActiveDriverDTO
import com.coursework.mainservice.dto.DriverDTO
import com.coursework.mainservice.dto.TariffDTO
import com.coursework.mainservice.dto.TripDTO
import com.coursework.mainservice.service.LogicServiceClient
import com.coursework.mainservice.service.NotificationClient
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
class LogicControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val logicServiceClient = Mockito.mock(LogicServiceClient::class.java)
    private val notificationClient = Mockito.mock(NotificationClient::class.java)

    @Test
    fun `getAllDrivers should return list of drivers`() {
        val mockDrivers = listOf(
            DriverDTO(1L, "John", "Doe", "1234567890", "john.doe@example.com", "password", 4.5, true),
            DriverDTO(2L, "Jane", "Smith", "0987654321", "jane.smith@example.com", "password", 4.8, false)
        )

        Mockito.`when`(logicServiceClient.getAllDrivers()).thenReturn(mockDrivers)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/logic/drivers/all"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jane"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("John"))
    }

    @Test
    fun `getActiveDrivers should return list of active drivers`() {
        val mockActiveDrivers = listOf(
            ActiveDriverDTO(1L, "John", "Doe", "1234567890", "john.doe@example.com", 4.5, true)
        )

        Mockito.`when`(logicServiceClient.getActiveDrivers()).thenReturn(mockActiveDrivers)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/logic/drivers/active"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John"))
    }

    @Test
    fun `getInactiveDrivers should return list of inactive drivers`() {
        val mockInactiveDrivers = listOf(
            DriverDTO(2L, "Jane", "Smith", "0987654321", "jane.smith@example.com", "password", 4.8, false)
        )

        Mockito.`when`(logicServiceClient.getInactiveDrivers()).thenReturn(mockInactiveDrivers)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/logic/drivers/inactive"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Mike"))
    }

    @Test
    fun `getLastTrips should return list of last trips`() {
        val mockTrips = listOf(
            TripDTO(1L, 1L, 1L, 1L, 1L, 3L, 5.0, 4.9, "Luxury", BigDecimal("100.0"), LocalDateTime.now(), LocalDateTime.now())
        )

        Mockito.`when`(logicServiceClient.getLastTrips()).thenReturn(mockTrips)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/logic/last-trips"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].service").isNotEmpty)
    }

    @Test
    fun `getTrips should return list of trips`() {
        val mockTrips = listOf(
            TripDTO(1L, 1L, 1L, 1L, 1L, 1L, 5.0, 4.9, "Luxury", BigDecimal("100.0"), LocalDateTime.now(), LocalDateTime.now())
        )

        Mockito.`when`(logicServiceClient.getTrips()).thenReturn(mockTrips)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/logic/trips"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].service").isNotEmpty)
    }

    @Test
    fun `analyzeDailyRevenue should return revenue data`() {
        val mockRevenue = listOf("2025-01-01" to 500.0)

        Mockito.`when`(logicServiceClient.analyzeDailyRevenue()).thenReturn(mockRevenue)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/logic/financial/revenue"))
            .andExpect(MockMvcResultMatchers.status().isOk)

    }

    @Test
    fun `getTripRatings should return trip ratings`() {
        val mockRatings = listOf(4.5 to 4.8)

        Mockito.`when`(logicServiceClient.getTripRatings()).thenReturn(mockRatings)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/logic/ratings/trips"))
            .andExpect(MockMvcResultMatchers.status().isOk)

    }

    @Test
    fun `getTariffsByCompany should return list of tariffs`() {
        val mockTariffs = listOf(
            TariffDTO(1L, "Standard", "10".toBigDecimal(), 1L, "Company A")
        )

        Mockito.`when`(logicServiceClient.getTariffsByCompany(1L)).thenReturn(mockTariffs)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/logic/tariffs/company/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Standard"))
    }

    @Test
    fun `downloadPdf should return PDF as byte array`() {
        val mockPdfBytes = ByteArray(100) { 0x0A }

        Mockito.`when`(notificationClient.generatePdf(Mockito.anyList())).thenReturn(mockPdfBytes)

        mockMvc.perform(MockMvcRequestBuilders.get("/download"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=trip_analytics_report.pdf"))

    }
}
