package mohammad.toriq.mynews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mohammad.toriq.mynews.data.repository.RepositoryImpl
import mohammad.toriq.mynews.data.source.remote.dto.APIService
import mohammad.toriq.mynews.domain.repository.Repository
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@Module
@InstallIn(SingletonComponent::class)
class ArticlesModule {
    @Provides
    @Singleton
    fun provideRepositoryImpl(
        articleApiService: APIService,
    ): Repository = RepositoryImpl(articleApiService)

}