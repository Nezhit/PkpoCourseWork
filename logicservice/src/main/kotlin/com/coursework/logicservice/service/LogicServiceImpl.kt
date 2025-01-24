package com.coursework.logicservice.service

import com.coursework.logic.*
import com.coursework.logic.LogicServiceOuterClass.Empty
import com.coursework.logic.LogicServiceOuterClass.UpdateDriverRequest
import com.coursework.logicservice.entity.ActiveDriver
import com.coursework.logicservice.entity.Company
import com.coursework.logicservice.entity.Driver
import com.coursework.logicservice.entity.Tariff
import com.coursework.logicservice.ext.toDTO
import com.coursework.logicservice.ext.toEntity
import com.coursework.logicservice.ext.toProto
import com.coursework.logicservice.repository.*
import io.grpc.stub.StreamObserver
import org.springframework.grpc.server.service.GrpcService

@GrpcService
class LogicServiceImpl(
    private val driverService: DriverService,
    private val financialAnalysisService: FinancialAnalysisService,
    private val ratingService: RatingService,
    private val tripService: TripService,
    private val tariffService: TariffService,
    private val tripRepository: TripRepository,
    private val clientRepository: ClientRepository,
    private val driverRepository: DriverRepository,
    private val companyRepository: CompanyRepository,
    private val tariffRepository: TariffRepository
) : LogicServiceGrpc.LogicServiceImplBase() {

    // DriverService
    override fun getAllDrivers(request: LogicServiceOuterClass.Empty, responseObserver: StreamObserver<LogicServiceOuterClass.DriverDTOListResponse>) {
        val drivers = driverService.getAllDrivers()
        val response = LogicServiceOuterClass.DriverDTOListResponse.newBuilder()
            .addAllDrivers(drivers.map { it.toProto() })
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getActiveDrivers(request: LogicServiceOuterClass.Empty, responseObserver: StreamObserver<LogicServiceOuterClass.ActiveDriverDTOListResponse>) {
        val activeDrivers = driverService.getActiveDrivers()
        val response = LogicServiceOuterClass.ActiveDriverDTOListResponse.newBuilder()
            .addAllDrivers(activeDrivers.map { it.toDTO() }.map { it.toProto() })
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
    override fun getLastTrips(request: LogicServiceOuterClass.Empty, responseObserver: StreamObserver<LogicServiceOuterClass.TripDTOListResponse>){
        val lastTrips = tripService.getLastTrips()
        val response = LogicServiceOuterClass.TripDTOListResponse.newBuilder()
            .addAllTrips(lastTrips.map { it.toDTO() }.map { it.toProto() })
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
    override fun getTrips(request: LogicServiceOuterClass.Empty, responseObserver: StreamObserver<LogicServiceOuterClass.TripDTOListResponse>){
        val lastTrips = tripService.getAllTrips()
        val response = LogicServiceOuterClass.TripDTOListResponse.newBuilder()
            .addAllTrips(lastTrips.map { it.toDTO() }.map { it.toProto() })
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }



    override fun saveTrip (request: LogicServiceOuterClass.SaveTripRequest,responseObserver: StreamObserver<LogicServiceOuterClass.Empty>){
        val trip = request.trip.toEntity(clientRepository,driverRepository,companyRepository,tariffRepository)
        tripService.saveTrip(trip)
        responseObserver.onNext(LogicServiceOuterClass.Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }
    override fun getInactiveDrivers(request: LogicServiceOuterClass.Empty, responseObserver: StreamObserver<LogicServiceOuterClass.DriverDTOListResponse>) {
        val drivers = driverService.getInactiveDrivers()
        val response = LogicServiceOuterClass.DriverDTOListResponse.newBuilder()
            .addAllDrivers(drivers.map { it.toProto() })
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun updateDriver(request: LogicServiceOuterClass.UpdateDriverRequest, responseObserver: StreamObserver<LogicServiceOuterClass.DriverResponse>) {
        val updatedDriver = driverService.updateDriver(request.id, request.driver.toEntity())
        val response = LogicServiceOuterClass.DriverResponse.newBuilder()
            .setDriver(updatedDriver.toDTO().toProto())
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
    override fun updateReadyDriver(request: LogicServiceOuterClass.UpdateReadyDriverRequest,responseObserver: StreamObserver<LogicServiceOuterClass.Empty>){
        driverService.updateReadyDriver(request.id,request.ready)
        responseObserver.onNext(LogicServiceOuterClass.Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun deleteDriver(request: LogicServiceOuterClass.DeleteDriverRequest, responseObserver: StreamObserver<LogicServiceOuterClass.Empty>) {
        driverService.deleteDriver(request.id)
        responseObserver.onNext(LogicServiceOuterClass.Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    // FinancialAnalysisService
    override fun analyzeDailyRevenue(request: LogicServiceOuterClass.Empty, responseObserver: StreamObserver<LogicServiceOuterClass.DailyRevenueResponse>) {
        val dailyRevenues = financialAnalysisService.analyzeDailyRevenue()
        val response = LogicServiceOuterClass.DailyRevenueResponse.newBuilder()
            .addAllDailyRevenues(
                dailyRevenues.map { (date, revenue) ->
                    LogicServiceOuterClass.DailyRevenue.newBuilder()
                        .setDate(date.toString())
                        .setRevenue(revenue.toDouble())
                        .build()
                }
            )
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    // RatingService
    override fun getTripRatings(request: LogicServiceOuterClass.Empty, responseObserver: StreamObserver<LogicServiceOuterClass.TripRatingsResponse>) {
        val ratings = ratingService.getTripRatings()
        val response = LogicServiceOuterClass.TripRatingsResponse.newBuilder()
            .addAllTripRatings(
                ratings.map { (driverRate, clientRate) ->
                    LogicServiceOuterClass.TripRating.newBuilder()
                        .setDriverRating(driverRate ?: 0.0)
                        .setClientRating(clientRate ?: 0.0)
                        .build()
                }
            )
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    // TariffService
    override fun getTariffsByCompany(request: LogicServiceOuterClass.GetTariffsRequest, responseObserver: StreamObserver<LogicServiceOuterClass.TariffDTOListResponse>) {
        val tariffs = tariffService.getTariffsByCompany(request.companyId)
        val response = LogicServiceOuterClass.TariffDTOListResponse.newBuilder()
            .addAllTariffs(tariffs.map { it.toProto() })
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun updateTariff(request: LogicServiceOuterClass.UpdateTariffRequest, responseObserver: StreamObserver<LogicServiceOuterClass.TariffResponse>) {
        val updatedTariff = tariffService.updateTariff(request.id, request.tariff.toEntity())
        val response = LogicServiceOuterClass.TariffResponse.newBuilder()
            .setTariff(updatedTariff.toProto())
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    // Extension functions to convert entities to proto objects and vice versa
    private fun Driver.toProto() = LogicServiceOuterClass.DriverDTO.newBuilder()
        .setId(id!!)
        .setName(name)
        .setSurname(surname)
        .setPhone(phone)
        .setEmail(email)
        .setPassword(password)
        .setRating(rating)
        .setReady(ready)

        .build()

    private fun Tariff.toProto() = LogicServiceOuterClass.TariffDTO.newBuilder()
        .setId(id!!)
        .setName(name)
        .setValue(value.toDouble())
        .setCompanyId(company.id!!)
        .setCompanyName(company.name)
        .build()



    private fun LogicServiceOuterClass.DriverDTO.toEntity() = Driver(
        id = id,
        name = name,
        surname = surname,
        phone = phone,
        email = email,
        password = password,
        rating = rating,
        ready = ready
    )

    private fun LogicServiceOuterClass.TariffDTO.toEntity() = Tariff(
        id = id,
        name = name,
        value = value.toBigDecimal(),
        company = Company(
            id = companyId,
            name = companyName
        )
    )
}