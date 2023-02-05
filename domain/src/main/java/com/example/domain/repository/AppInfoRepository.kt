package com.example.domain.repository

interface AppInfoRepository {
    fun setTimestamp(key: String, timestamp: Long)

    fun getTimestamp(key: String, def: Long): Long

    fun setLoadPages(key: String, page: Int)

    fun getLoadPages(key: String, def: Int): Int
}