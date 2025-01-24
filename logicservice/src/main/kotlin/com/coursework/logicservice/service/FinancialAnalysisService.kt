package com.coursework.logicservice.service

import com.coursework.logicservice.repository.TripRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate

@Service
class FinancialAnalysisService(
    private val tripRepository: TripRepository
) {

    // Анализ стоимости поездок, сгруппированный по дням
    fun analyzeDailyRevenue(): Map<LocalDate, BigDecimal> {
        return tripRepository.findAll()
            .groupBy { it.startDate.toLocalDate() }
            .mapValues { entry ->
                entry.value.sumOf { it.price }
            }
    }
}
