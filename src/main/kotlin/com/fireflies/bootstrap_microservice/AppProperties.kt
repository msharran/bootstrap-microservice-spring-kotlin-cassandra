package com.fireflies.bootstrap_microservice

object AppProperties {
    const val MICROSERVICE_URL_NAME = "bootstrap"
    const val MICROSERVICE_NAME = "bootstrap_microservice"
    const val BASE_URL = "/api/$MICROSERVICE_URL_NAME/v1"

    object Cassandra {
        const val KEYSPACE_NAME = MICROSERVICE_NAME
        const val REPLICATION_FACTOR = 1L
        const val NODES = "127.0.0.1:9042" // comma separated nodes
        val MODELS = arrayOf("com.fireflies.bootstrap_microservice.model")
    }

    object AuthService {
        const val V1_BASE_URL = "http://localhost:10001/api/auth/v1"
    }

    object Security {
        const val SECRET = "TheFirefliesJwtSecret"
        const val BEARER_ = "Bearer "
        const val AUTHORIZATION = "Authorization"

        const val ADMIN_ENDPOINTS = "/api/$MICROSERVICE_URL_NAME/v1/admin/*"
        val AUTHORIZATION_IGNORED_ENDPOINTS = arrayOf<String>()
    }
}