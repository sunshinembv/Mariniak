package com.example.data.repository

import android.content.SharedPreferences
import com.example.domain.repository.AppInfoRepository
import javax.inject.Inject

class AppInfoRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferences
) : AppInfoRepository {
    override fun setTimestamp(key: String, timestamp: Long) {
        preferences.edit().putLong(key, timestamp).apply()
    }

    override fun getTimestamp(key: String, def: Long): Long = preferences.getLong(key, def)

    override fun setLoadPages(key: String, page: Int) {
        preferences.edit().putInt(key, page).apply()
    }

    override fun getLoadPages(key: String, def: Int): Int = preferences.getInt(key, def)
}