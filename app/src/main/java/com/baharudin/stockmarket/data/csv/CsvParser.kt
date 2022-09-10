package com.baharudin.stockmarket.data.csv

import java.io.InputStream
import java.io.InputStreamReader

interface CsvParser<T> {
    suspend fun parse(stream : InputStream) : List<T>
}