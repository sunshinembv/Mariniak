package com.example.domain.usecases

import com.example.domain.repository.AppInfoRepository

class GetLoadPagesUseCase(private val appInfoRepository: AppInfoRepository) {
    fun execute(key: String, def: Int): Int {
        return appInfoRepository.getLoadPages(key, def)
    }
}