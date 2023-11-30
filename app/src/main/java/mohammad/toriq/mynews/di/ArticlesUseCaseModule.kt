package mohammad.toriq.mynews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mohammad.toriq.mynews.domain.usecase.GetArticlesUseCase
import mohammad.toriq.mynews.domain.repository.Repository
import mohammad.toriq.mynews.domain.usecase.GetSourcesUseCase
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@Module
@InstallIn(SingletonComponent::class)
class ArticlesUseCaseModule {

    @Provides
    @Singleton
    fun provideGetArticlesUseCase(repository: Repository): GetArticlesUseCase =
        GetArticlesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSourcesUseCase(repository: Repository): GetSourcesUseCase =
        GetSourcesUseCase(repository)
}