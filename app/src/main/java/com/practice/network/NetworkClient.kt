package com.practice.network

import android.util.Log
import com.practice.network.NetworkInterceptor.interceptor
import com.practice.network.NetworkUrls.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NetworkClient {
    private val ktorClient = HttpClient {
        // default details
        defaultRequest {
            url(BASE_URL)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        // add logs for details
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.w("", "log: $message")
                }
            }
            level = LogLevel.ALL
        }
        // set timers
        install(HttpTimeout) {
            connectTimeoutMillis = 30_0000
            socketTimeoutMillis = 10_0000
            requestTimeoutMillis = 10_0000
        }
        // add interceptor
        install(interceptor)

        // add serialization
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun get(
        endPoint: String,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return ktorClient.get(endPoint) {
            headers.forEach { (k, v) -> header(k, v) }
        }
    }

    suspend fun post(
        url: String, headers: Map<String, String> = emptyMap(), body: Any
    ): HttpResponse {
        return ktorClient.post(url) {
            headers.forEach { (key, value) -> header(key, value) }
            setBody(body)
        }
    }

    suspend fun put(
        url: String, headers: Map<String, String> = emptyMap(), body: Any
    ): HttpResponse {
        return ktorClient.put(url) {
            headers.forEach { (key, value) -> header(key, value) }
            setBody(body)
        }
    }
}