package jp.co.yumemi.android.code_check.api

import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import jp.co.yumemi.android.code_check.entity.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @GET("/search/repositories")
    suspend fun findRepositories(
        @Query("q") query: String
    ): Response<Search>
}

