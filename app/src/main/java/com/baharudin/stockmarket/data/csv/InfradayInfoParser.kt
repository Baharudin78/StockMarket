package com.baharudin.stockmarket.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.baharudin.stockmarket.data.mapper.toInfradayInfo
import com.baharudin.stockmarket.data.remote.dto.InfradayInfoDto
import com.baharudin.stockmarket.domain.model.CompanyListing
import com.baharudin.stockmarket.domain.model.InfradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InfradayInfoParser @Inject constructor() :  CsvParser<InfradayInfo> {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun  parse(stream: InputStream): List<InfradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = InfradayInfoDto(timestamp, close.toDouble())
                    dto.toInfradayInfo()
                }
                .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(4).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}