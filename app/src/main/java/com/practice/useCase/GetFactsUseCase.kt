package com.practice.useCase

import com.practice.model.facts.FactsModel
import com.practice.network.NetworkRequestImpl
import com.practice.network.NetworkResponse
import com.practice.network.onNetworkResponse
import kotlinx.coroutines.flow.Flow

class GetFactsUseCase {
    suspend operator fun invoke(): Flow<NetworkResponse<FactsModel?>> {
        return onNetworkResponse {
            NetworkRequestImpl.getFacts()
        }
    }
}