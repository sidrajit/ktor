package com.practice.ktorPractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.model.facts.FactsModel
import com.practice.network.NetworkResponse
import com.practice.useCase.GetFactsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivityVM : ViewModel() {
    private val getFactsUseCase = GetFactsUseCase()

    private val _getFactsResponse =
        MutableStateFlow<NetworkResponse<FactsModel?>>(NetworkResponse.LOADING())
    val getFactsResponse = _getFactsResponse.asStateFlow()

    fun getFacts() = viewModelScope.launch {
        getFactsUseCase().onEach {
            _getFactsResponse.emit(it)
        }.launchIn(this)
    }
}