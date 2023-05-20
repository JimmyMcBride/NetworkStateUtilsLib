package dev.jimmymcbride.network_state_utils_lib.di

import dev.jimmymcbride.network_state_utils_lib.data.remote.CitiesApi
import dev.jimmymcbride.network_state_utils_lib.data.repository.RepositoryImpl
import dev.jimmymcbride.network_state_utils_lib.domain.repository.Repository
import dev.jimmymcbride.network_state_utils_lib.domain.use_cases.AddCityUseCase
import dev.jimmymcbride.network_state_utils_lib.domain.use_cases.CityUseCases
import dev.jimmymcbride.network_state_utils_lib.domain.use_cases.DeleteCityUseCase
import dev.jimmymcbride.network_state_utils_lib.domain.use_cases.GetCitiesUseCase
import dev.jimmymcbride.network_state_utils_lib.domain.use_cases.GetCityUseCase
import dev.jimmymcbride.network_state_utils_lib.domain.use_cases.UpdateCityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesCitiesApi() = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create<CitiesApi>()

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        citiesApi: CitiesApi
    ): Repository =
        RepositoryImpl(citiesApi)

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository) = CityUseCases(
        getCitiesUseCase = GetCitiesUseCase(repository),
        getCityUseCase = GetCityUseCase(repository),
        addCityUseCase = AddCityUseCase(repository),
        updateCityUseCase = UpdateCityUseCase(repository),
        deleteCityUseCase = DeleteCityUseCase(repository),
    )
}