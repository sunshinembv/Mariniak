package com.example.domain.usecases

import com.example.domain.repository.AppInfoRepository

class SaveTimeUpdateUseCase(private val appInfoRepository: AppInfoRepository) {
    fun execute(key: String, time: Long) {
        return appInfoRepository.setTimestamp(key, time)
    }
}