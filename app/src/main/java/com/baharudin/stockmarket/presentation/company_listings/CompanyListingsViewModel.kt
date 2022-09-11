package com.baharudin.stockmarket.presentation.company_listings

import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudin.stockmarket.domain.repository.StockRepository
import com.baharudin.stockmarket.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel(){
    var state by mutableStateOf(CompanyListingsState())
    private var searchJob : Job? = null

    fun onEvent(event: CompanyListingsEvent) {
        when(event) {
            is CompanyListingsEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingsEvent.OnSearchQueryChanged -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }
    init {
        getCompanyListings()
    }
    fun getCompanyListings(
        query : String = state.searchQuery.lowercase(),
        fetchFromRemote : Boolean = false
    ) {
        viewModelScope.launch {
            stockRepository
                .getCompanyListings(fetchFromRemote, query)
                .collect { result ->
                    when(result) {
                        is Resource.Success-> {
                            result.data?.let { listing ->
                                state = state.copy(
                                    company = listing
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}