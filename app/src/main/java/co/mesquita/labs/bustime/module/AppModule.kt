package co.mesquita.labs.bustime.module

import co.mesquita.labs.bustime.repository.BusRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideBusRepository() : BusRepository {
        return BusRepository()
    }
}
