package com.coursework.logicservice.service

import com.coursework.logicservice.entity.Tariff
import com.coursework.logicservice.repository.CompanyRepository
import com.coursework.logicservice.repository.TariffRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class TariffService(
    private val tariffRepository: TariffRepository,
    private val companyRepository: CompanyRepository
) {

    // Получить список тарифов по компаниям
    fun getTariffsByCompany(companyId: Long): List<Tariff> {
        return tariffRepository.findAllByCompanyId(companyId)
    }

    // Редактировать тариф
    fun updateTariff(tariffId: Long, updatedTariff: Tariff): Tariff {
        val tariff = tariffRepository.findById(tariffId)
            .orElseThrow { EntityNotFoundException("Tariff with ID $tariffId not found") }

        tariff.apply {
            name = updatedTariff.name
            value = updatedTariff.value
            company = updatedTariff.company
        }
        return tariffRepository.save(tariff)
    }
}
