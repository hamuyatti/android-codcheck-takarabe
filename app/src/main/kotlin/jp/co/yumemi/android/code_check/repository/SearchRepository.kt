package jp.co.yumemi.android.code_check.repository

interface SearchRepository {
    suspend fun fetchRepository()
}