package jp.co.yumemi.android.code_check.webApi

import jp.co.yumemi.android.code_check.entity.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/search/repositories")
    suspend fun fetchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String
    ): Response<Search>
}

