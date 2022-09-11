package com.baharudin.stockmarket.presentation.company_info

import com.baharudin.stockmarket.domain.model.CompanyInfo
import com.baharudin.stockmarket.domain.model.InfradayInfo

data class CompanyInfoState(
    val stockInfos : List<InfradayInfo> = emptyList(),
    val company : CompanyInfo? = null,
    val isLoading : Boolean = false,
    val error : String? = null
)
