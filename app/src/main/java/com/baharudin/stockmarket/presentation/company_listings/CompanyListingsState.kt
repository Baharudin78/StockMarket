package com.baharudin.stockmarket.presentation.company_listings

import com.baharudin.stockmarket.domain.model.CompanyListing

data class CompanyListingsState(
    val company : List<CompanyListing> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val searchQuery : String = ""
)
