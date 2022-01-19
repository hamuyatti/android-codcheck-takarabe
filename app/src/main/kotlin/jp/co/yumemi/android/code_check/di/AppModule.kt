package jp.co.yumemi.android.code_check.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.webApi.Api
import jp.co.yumemi.android.code_check.repository.SearchRepository
import jp.co.yumemi.android.code_check.repository.SearchRepositoryImpl
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSearchRepository(api: Api): SearchRepository = SearchRepositoryImpl(api = api)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        val format = Json { ignoreUnknownKeys = true}
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addConverterFactory(format.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(
      retrofit: Retrofit
    ): Api {
        return retrofit.create(Api::class.java)
    }
}
