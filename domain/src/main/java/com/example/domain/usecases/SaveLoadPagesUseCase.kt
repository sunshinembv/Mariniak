package com.example.domain.usecases

import com.example.domain.repository.AppInfoRepository

class SaveLoadPagesUseCase(private val appInfoRepository: AppInfoRepository) {
    fun execute(key: String, page: Int) {
        return appInfoRepository.setLoadPages(key, page)
    }
}