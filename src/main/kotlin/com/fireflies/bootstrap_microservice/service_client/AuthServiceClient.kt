package com.fireflies.bootstrap_microservice.service_client

import com.fireflies.bootstrap_microservice.AppProperties
import com.fireflies.bootstrap_microservice.payload.UserCredential
import com.fireflies.bootstrap_microservice.response.APIResponse
import com.fireflies.bootstrap_microservice.util.awaitModel
import com.fireflies.bootstrap_microservice.util.webClientOf
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange

object AuthServiceClient {
    private val webClient: WebClient by lazy {
        webClientOf(AppProperties.AuthService.V1_BASE_URL)
    }

    @Throws(Exception::class)
    suspend fun verifyUser(token: String, username: String): UserCredential = webClient
        .get()
        .uri("/user?username=$username")
        .header(AppProperties.Security.AUTHORIZATION, "${AppProperties.Security.BEARER_}$token")
        .accept(MediaType.APPLICATION_JSON)
        .awaitExchange {
            it.awaitModel<APIResponse<UserCredential>>().data
        }
}