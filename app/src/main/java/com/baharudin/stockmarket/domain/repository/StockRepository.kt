package com.baharudin.stockmarket.domain.repository

import com.baharudin.stockmarket.domain.model.CompanyListing
import com.baharudin.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote : Boolean,
        query : String
    ) : Flow<Resource<List<CompanyListing>>>
}