package com.practice.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

/**
 * network class response
 * */
sealed class NetworkResponse<T> {
    class LOADING<T> : NetworkResponse<T>()
    class SUCCESS<T>(val data: T?) : NetworkResponse<T>()
    class ERROR<T>(val message: String) : NetworkResponse<T>()
}

/**
 * getting network response using flow
 * */
inline fun <reified T> onNetworkResponse(crossinline request: suspend () -> HttpResponse): Flow<NetworkResponse<T?>> {
    return flow {
        try {
            val response = request()
            emit(NetworkResponse.LOADING())
            if (response.status.isSuccess()) {
                emit(NetworkResponse.SUCCESS(response.body<T>()))
            } else {
                emit(NetworkResponse.ERROR(response.status.description))
            }
        } catch (e: Exception) {
            emit(NetworkResponse.ERROR(e.message.orEmpty()))
        }
    }
}

suspend fun <T> Flow<NetworkResponse<T?>>.collectResponseState(
    isLoading: () -> Unit = {},
    isSuccess: T?.() -> Unit = {},
    isError: String.() -> Unit = {}
) {
    this.collectLatest {
        when (it) {
            is NetworkResponse.LOADING -> isLoading()
            is NetworkResponse.SUCCESS -> isSuccess(it.data)
            is NetworkResponse.ERROR -> isError(it.message)
        }
    }
}