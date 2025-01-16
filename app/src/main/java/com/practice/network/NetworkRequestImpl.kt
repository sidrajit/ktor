package com.practice.network

import com.practice.model.facts.FactsModel
import com.practice.network.NetworkUrls.FACT
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody

object NetworkRequestImpl : NetworkRequest {
    override suspend fun getFacts() = NetworkClient.get(FACT)
    override suspend fun postFacts(factsModel: FactsModel) =
        NetworkClient.post(FACT, body = HttpRequestBuilder().setBody(factsModel))
}