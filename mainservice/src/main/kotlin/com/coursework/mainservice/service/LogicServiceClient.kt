package com.coursework.mainservice.service

import com.coursework.logic.LogicServiceGrpc
import com.coursework.logic.LogicServiceOuterClass
import com.coursework.mainservice.dto.ActiveDriverDTO
import com.coursework.mainservice.dto.DriverDTO
import com.coursework.mainservice.dto.TariffDTO
import com.coursework.mainservice.dto.TripDTO
import com.coursework.mainservice.ext.toDTO
import com.coursework.mainservice.ext.toProto
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Service

@Service
class LogicServiceClient {

    private val channel = ManagedChannelBuilder.forAddress("localhost", 9091) // Порт logicservice
        .usePlaintext()
        .build()

    private val logicServiceStub = LogicServiceGrpc.newBlockingStub(channel)

    // DriverService
    fun getAllDrivers(): List<DriverDTO> {
        val response = logicServiceStub.getAllDrivers(LogicServiceOuterClass.Empty.getDefaultInstance()).driversList.map { it.toDTO() }
        return response
    }

    fun getActiveDrivers(): List<ActiveDriverDTO> {
        val response = logicServiceStub.getActiveDrivers(LogicServiceOuterClass.Empty.getDefaultInstance()).driversList.map { it.toDTO() }
        return response
    }
    fun getLastTrips(): List<TripDTO>{
        val response = logicServiceStub.getLastTrips(LogicServiceOuterClass.Empty.getDefaultInstance()).tripsList.map { it.toDTO() }
        return response
    }
    fun getTrips(): List<TripDTO>{
        val response = logicServiceStub.getTrips(LogicServiceOuterClass.Empty.getDefaultInstance()).tripsList.map { it.toDTO() }
        return response
    }
    fun saveTrip(tripDTO: TripDTO){
        logicServiceStub.saveTrip(LogicServiceOuterClass.SaveTripRequest.newBuilder()
            .setTrip(tripDTO.toProto())
            .build()
        )
    }
    fun getInactiveDrivers(): List<DriverDTO> {
        val response = logicServiceStub.getInactiveDrivers(LogicServiceOuterClass.Empty.getDefaultInstance()).driversList.map { it.toDTO() }
        return response
    }

    fun updateDriver(driverId: Long, updatedDriver: DriverDTO): DriverDTO {
        val request = LogicServiceOuterClass.UpdateDriverRequest.newBuilder()
            .setId(driverId)
            .setDriver(updatedDriver.toProto())
            .build()

        val response = logicServiceStub.updateDriver(request).driver.toDTO()
        return response
    }
    fun updateReadyDriver(driverId: Long,ready: Boolean){
        val request = LogicServiceOuterClass.UpdateReadyDriverRequest.newBuilder()
            .setId(driverId)
            .setReady(ready)
            .build()
        logicServiceStub.updateReadyDriver(request)
    }

    fun deleteDriver(driverId: Long) {
        val request = LogicServiceOuterClass.DeleteDriverRequest.newBuilder()
            .setId(driverId)
            .build()

        logicServiceStub.deleteDriver(request)
    }

    // FinancialAnalysisService
    fun analyzeDailyRevenue(): List<Pair<String, Double>> {
        val response = logicServiceStub.analyzeDailyRevenue(LogicServiceOuterClass.Empty.getDefaultInstance())
        return response.dailyRevenuesList.map { it.date to it.revenue }
    }

    // RatingService
    fun getTripRatings(): List<Pair<Double, Double>> {
        val response = logicServiceStub.getTripRatings(LogicServiceOuterClass.Empty.getDefaultInstance())
        return response.tripRatingsList.map { it.driverRating to it.clientRating }
    }

    // TariffService
    fun getTariffsByCompany(companyId: Long): List<TariffDTO> {
        val request = LogicServiceOuterClass.GetTariffsRequest.newBuilder()
            .setCompanyId(companyId)
            .build()

        val response = logicServiceStub.getTariffsByCompany(request).tariffsList.map { it.toDTO() }
        return response
    }

    fun updateTariff(tariffId: Long, updatedTariff: TariffDTO): TariffDTO {
        val request = LogicServiceOuterClass.UpdateTariffRequest.newBuilder()
            .setId(tariffId)
            .setTariff(updatedTariff.toProto())
            .build()

        val response = logicServiceStub.updateTariff(request).tariff.toDTO()
        return response
    }

    // Cleanup resources (optional)
    fun shutdown() {
        channel.shutdown()
    }
}
