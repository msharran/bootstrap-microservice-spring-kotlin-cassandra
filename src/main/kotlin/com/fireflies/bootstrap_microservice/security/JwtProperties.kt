package com.fireflies.bootstrap_microservice.security

import com.fireflies.bootstrap_microservice.AppProperties
import com.fireflies.bootstrap_microservice.configuration.AppConfiguration

object JwtProperties {
    const val SECRET = "TheFirefliesJwtSecret"
    const val BEARER_ = "Bearer "
    const val AUTHORIZATION = "Authorization"

    const val ADMIN_ENDPOINTS = "/api/${AppProperties.MICROSERVICE_URL_NAME}/v1/admin/*"
    val AUTHORIZATION_IGNORED_ENDPOINTS = arrayOf<String>()
}