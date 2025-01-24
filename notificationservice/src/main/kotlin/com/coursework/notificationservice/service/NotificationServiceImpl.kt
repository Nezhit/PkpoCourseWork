package com.coursework.notificationservice.service

import com.coursework.notification.*
import com.coursework.notificationservice.ext.toDto
import com.coursework.notificationservice.ext.toProto
import io.grpc.stub.StreamObserver
import org.springframework.grpc.server.service.GrpcService

@GrpcService
class NotificationServiceImpl(
    private val pdfService: PdfService
) : NotificationServiceGrpc.NotificationServiceImplBase() {

    private val notificationStorage = mutableMapOf<String, MutableList<String>>()

    override fun sendNotification(request: Notification.NotificationRequest, responseObserver: StreamObserver<Notification.NotificationResponse>) {
        val message = "Notification for ${request.userId}: ${request.message}"
        notificationStorage.getOrPut(request.userId) { mutableListOf() }.add(message)

        val response = Notification.NotificationResponse.newBuilder()
            .setSuccess(true)
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getNotifications(request: Notification.NotificationHistoryRequest, responseObserver: StreamObserver<Notification.NotificationHistoryResponse>) {
        val history = notificationStorage[request.userId] ?: emptyList()
        val response = Notification.NotificationHistoryResponse.newBuilder()
            .addAllNotifications(history)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun generatePdf(
        request: Notification.GeneratePdfRequest,
        responseObserver: StreamObserver<Notification.GeneratePdfResponse>?
    ) {
        try {
            val trips = request.tripsList.map { it.toDto() }

            val pdfBytes = pdfService.generatePdf(trips)

            val response = Notification.GeneratePdfResponse.newBuilder()
                .setPdfFile(pdfBytes.toProto())
                .build()

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()
        } catch (e: Exception) {
            println("AAAA = " + e.message)
            responseObserver?.onError(e)
        }
    }
}