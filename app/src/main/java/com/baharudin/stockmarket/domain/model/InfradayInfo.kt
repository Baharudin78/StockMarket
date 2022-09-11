package com.baharudin.stockmarket.domain.model

import java.time.LocalDateTime

data class InfradayInfo(
    val date : LocalDateTime,
    val close : Double
)
