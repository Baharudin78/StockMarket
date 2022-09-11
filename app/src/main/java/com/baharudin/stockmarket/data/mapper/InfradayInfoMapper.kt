package com.baharudin.stockmarket.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.baharudin.stockmarket.data.remote.dto.InfradayInfoDto
import com.baharudin.stockmarket.domain.model.InfradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun InfradayInfoDto.toInfradayInfo() : InfradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timeStamp, formatter)
    return InfradayInfo(
        date = localDateTime,
        close = close
    )
}