package com.practice.network

import com.practice.model.facts.FactsModel
import io.ktor.client.statement.HttpResponse

interface NetworkRequest {
    suspend fun getFacts(): HttpResponse
    suspend fun postFacts(factsModel: FactsModel) : HttpResponse
}