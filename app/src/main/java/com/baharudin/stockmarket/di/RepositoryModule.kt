package com.baharudin.stockmarket.di

import com.baharudin.stockmarket.data.csv.CompanyListingParser
import com.baharudin.stockmarket.data.csv.CsvParser
import com.baharudin.stockmarket.data.repository.StockRepositoryImpl
import com.baharudin.stockmarket.domain.model.CompanyListing
import com.baharudin.stockmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsCompanyListingParser(
        companyListingParser: CompanyListingParser
    ) : CsvParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindsStockRepository(
        stockRepositoryImpl : StockRepositoryImpl
    ) : StockRepository
}