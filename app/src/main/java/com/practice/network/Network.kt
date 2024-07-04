package com.practice.network

import android.util.Log
import com.practice.model.facts.FactsModel
import com.practice.network.Urls.BASE_URL
import com.practice.network.Urls.FACT
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.get
import io.ktor.client.statement.request
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class Network {
    private val _response = MutableStateFlow<FactsModel?>(null)
    val response get() = _response.asStateFlow()

    private val ktorClient = HttpClient {
        defaultRequest { url(BASE_URL) }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.w("", "log: $message")
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse {
                Log.w("httpsResponse", "${it.request.url} ${it.requestTime.seconds}  ${it.status}")
            }
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getFacts() {
        ktorClient.get(FACT).also { req ->
            if (req.status.value == 200) {
                _response.emit(req.body<FactsModel>())
            } else {
                Log.e("", "${req.status}")
            }
        }
    }
}