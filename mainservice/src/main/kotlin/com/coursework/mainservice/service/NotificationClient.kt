package com.coursework.mainservice.service

import com.coursework.mainservice.dto.TripDTO
import com.coursework.mainservice.ext.toProtoNotification
import com.coursework.notification.Notification
import com.coursework.notification.NotificationServiceGrpc
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Service

@Service
class NotificationClient() {

    private val channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build()

    private val notificationServiceStub = NotificationServiceGrpc.newBlockingStub(channel)

    fun sendNotification(userId: String, message: String): Boolean {
        val request = Notification.NotificationRequest.newBuilder()
            .setUserId(userId)
            .setMessage(message)
            .build()

        val response = notificationServiceStub.sendNotification(request)
        return response.success
    }

    fun getNotifications(userId: String): List<String> {
        val request = Notification.NotificationHistoryRequest.newBuilder()
            .setUserId(userId)
            .build()

        val response = notificationServiceStub.getNotifications(request)
        return response.notificationsList
    }
    fun generatePdf(list: List<TripDTO>):ByteArray{
        val request = Notification.GeneratePdfRequest.newBuilder()
            .addAllTrips(list.map { it.toProtoNotification() })
            .build()
        val response = notificationServiceStub.generatePdf(request).pdfFile.toByteArray()
        return response
    }
}