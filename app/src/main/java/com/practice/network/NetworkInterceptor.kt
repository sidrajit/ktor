package com.practice.network

import android.util.Log
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.http.HttpStatusCode

object NetworkInterceptor {
    val interceptor = createClientPlugin("Interceptor") {
        onRequest { request, _ ->
            try {
                println("🚀 Intercepting Request: ${request.url}")
                request.headers.append("Authorization", "Bearer YOUR_TOKEN")
                Log.d("", "On send =====>>>>> ${request.url}  ")
            } catch (e: ConnectTimeoutException) {
                println(" Connection Timeout: Could not connect to the server!")
            } catch (e: HttpRequestTimeoutException) {
                println(" Request Timeout: The server took too long to respond!")
            } catch (e: SocketTimeoutException) {
                println(" Socket Timeout: No data received from the server!")
            } catch (e: Exception) {
                println(" Unknown Error: ${e.message}")
            }
        }

        onResponse { response ->
            println("✅ Intercepted Response: ${response.status}")
            if (response.status == HttpStatusCode.Unauthorized) {
                println("⚠️ Unauthorized! Handle token refresh.")
                Log.d("", "On receive =====>>>>> ${response.status}  ")
            }
        }
    }
}