package com.baharudin.stockmarket.domain.repository

import com.baharudin.stockmarket.domain.model.CompanyInfo
import com.baharudin.stockmarket.domain.model.CompanyListing
import com.baharudin.stockmarket.domain.model.InfradayInfo
import com.baharudin.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote : Boolean,
        query : String
    ) : Flow<Resource<List<CompanyListing>>>

    suspend fun getInfradayInfo(
        symbol : String
    ) : Resource<List<InfradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ) : Resource<CompanyInfo>
}