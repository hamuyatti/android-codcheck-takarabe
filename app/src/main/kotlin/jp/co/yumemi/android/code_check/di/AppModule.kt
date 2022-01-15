package jp.co.yumemi.android.code_check.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.api.Api
import jp.co.yumemi.android.code_check.api.ApiImpl
import jp.co.yumemi.android.code_check.repository.SearchRepository
import jp.co.yumemi.android.code_check.repository.SearchRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSearchRepository(api: Api): SearchRepository = SearchRepositoryImpl(api = api)

    @Provides
    @Singleton
    fun provideApi(): Api = ApiImpl()
}
