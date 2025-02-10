package com.practice.ui.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.localStorage.DataStoreManager
import com.practice.localStorage.DataStorePreferenceKeys.MODEL
import com.practice.model.facts.FactsModel
import com.practice.network.NetworkResponse
import com.practice.useCase.GetFactsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivityVM(
    private val getFactsUseCase: GetFactsUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _getFactsResponse =
        MutableStateFlow<NetworkResponse<FactsModel?>>(NetworkResponse.LOADING())
    val getFactsResponse = _getFactsResponse.asStateFlow()

    fun getFacts() = viewModelScope.launch {
        getFactsUseCase().onEach {
            _getFactsResponse.emit(it)
        }.launchIn(this)
    }

    fun saveData(model: FactsModel?) {
        dataStoreManager.saveObject(MODEL, model)
        readData()
    }

    private fun readData() {
        dataStoreManager.readObject<FactsModel>(MODEL) {
            print("readObject: $it")
        }
    }
}