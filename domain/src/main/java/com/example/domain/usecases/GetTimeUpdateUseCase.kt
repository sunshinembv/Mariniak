package com.example.domain.usecases

import com.example.domain.repository.AppInfoRepository

class GetTimeUpdateUseCase(private val appInfoRepository: AppInfoRepository) {
    fun execute(key: String, def: Long): Long {
        return appInfoRepository.getTimestamp(key, def)
    }
}