package com.practice.network

import com.practice.model.facts.FactsModel
import com.practice.network.NetworkUrls.FACT
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody

class NetworkRequestImpl(private val networkClient: NetworkClient) : NetworkRequest {
    // repository
    override suspend fun getFacts() = networkClient.get(FACT)
    override suspend fun postFacts(factsModel: FactsModel) =
        networkClient.post(FACT, body = HttpRequestBuilder().setBody(factsModel))
}