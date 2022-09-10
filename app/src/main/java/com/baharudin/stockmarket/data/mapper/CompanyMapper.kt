package com.baharudin.stockmarket.data.mapper

import com.baharudin.stockmarket.data.local.CompanyListingEntity
import com.baharudin.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() : CompanyListing{
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity{
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}