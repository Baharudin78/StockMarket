package com.baharudin.stockmarket.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudin.stockmarket.domain.repository.StockRepository
import com.baharudin.stockmarket.util.Resource
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val saveStateHandle : SavedStateHandle,
    private val repository : StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())
    init {
        viewModelScope.launch {
            val symbol = saveStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val infradayInfoResult = async { repository.getInfradayInfo(symbol) }
            when(val result = companyInfoResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        company = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.messege,
                        company = null
                    )
                }
                else -> Unit
            }

            when(val result = infradayInfoResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        stockInfos = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.messege,
                        company = null
                    )
                }
                else -> Unit
            }
        }
    }
}