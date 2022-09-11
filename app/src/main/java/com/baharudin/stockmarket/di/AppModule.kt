package com.baharudin.stockmarket.di

import android.app.Application
import androidx.room.Room
import com.baharudin.stockmarket.data.local.StockDatabase
import com.baharudin.stockmarket.data.remote.StockApi
import com.baharudin.stockmarket.util.Constant.BASE_URL
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
    fun providesStockApi() : StockApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app : Application) : StockDatabase {
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            "stockdb.cb"
        ).build()
    }
}