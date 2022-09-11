package com.baharudin.stockmarket.data.repository

import com.baharudin.stockmarket.data.csv.CompanyListingParser
import com.baharudin.stockmarket.data.csv.CsvParser
import com.baharudin.stockmarket.data.local.StockDatabase
import com.baharudin.stockmarket.data.mapper.toCompanyListing
import com.baharudin.stockmarket.data.mapper.toCompanyListingEntity
import com.baharudin.stockmarket.data.remote.StockApi
import com.baharudin.stockmarket.domain.model.CompanyListing
import com.baharudin.stockmarket.domain.repository.StockRepository
import com.baharudin.stockmarket.util.Resource
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api : StockApi,
    private val db : StockDatabase,
    private val companyListingParser: CsvParser<CompanyListing>
) : StockRepository {

    private val dao = db.dao
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchQueryCompanyListing(query)
            emit(Resource.Success(
                data = localListing.map { it.toCompanyListing() }
            ))

            val isDbIsEmpty = localListing.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbIsEmpty && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            }catch (e : IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not load data"))
                null
            }catch (e : HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not load data"))
                null
            }
            remoteListing?.let { listing ->
                dao.clearCompanyListing()
                dao.insertCompanyListing(
                    listing.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao
                        .searchQueryCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }
}