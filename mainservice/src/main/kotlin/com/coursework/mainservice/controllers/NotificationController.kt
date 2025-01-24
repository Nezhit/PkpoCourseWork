package com.coursework.mainservice.controllers

import com.coursework.mainservice.dto.TripDTO
import com.coursework.mainservice.service.LogicServiceClient
import com.coursework.mainservice.service.NotificationClient
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.LocalDateTime

@RestController
class NotificationController(
    private val notificationClient: NotificationClient,
    private val logicServiceClient: LogicServiceClient
) {

    @GetMapping("/send-notification")
    fun sendNotification(@RequestParam userId: String, @RequestParam message: String): String {
        return try {
            notificationClient.sendNotification(userId, message)
            "Уведомление успешно отправлено!"
        } catch (e: Exception) {
            "Ошибка при отправке уведомления: ${e.message}"
        }
    }

    @GetMapping("/get-notifications")
    fun getNotifications(@RequestParam userId: String): List<String> {
        return try {
            notificationClient.getNotifications(userId)
        } catch (e: Exception) {
            listOf("Ошибка при получении уведомлений: ${e.message}")
        }
    }
    @GetMapping("/download")
    fun downloadPdf(): ResponseEntity<ByteArray> {
        val pdfBytes = notificationClient.generatePdf(
            logicServiceClient.getTrips()
        )
        return ResponseEntity(pdfBytes, HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=trip_analytics_report.pdf")
            contentType = MediaType.APPLICATION_PDF
        }, HttpStatus.OK)
    }
}