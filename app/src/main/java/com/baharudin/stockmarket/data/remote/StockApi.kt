package com.baharudin.stockmarket.data.remote

import com.baharudin.stockmarket.data.remote.dto.CompanyInfoDto
import com.baharudin.stockmarket.util.Constant.API_KEY
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey : String = API_KEY
    ): ResponseBody

    @GET("query?function=TIME_SERRIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getInfradayInfo(
        @Query("symbol") symbol : String,
        @Query("apikey") apiKey: String = API_KEY
    ) : ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ) : CompanyInfoDto
}