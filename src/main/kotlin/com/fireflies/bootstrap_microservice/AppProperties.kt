package com.fireflies.bootstrap_microservice

object AppProperties {
    const val MICROSERVICE_URL_NAME = "bootstrap"
    const val MICROSERVICE_NAME = "bootstrap_microservice"
    const val BASE_URL = "/api/$MICROSERVICE_URL_NAME/v1"

    //Database
    const val CASANDRA_NODES = "127.0.0.1" // comma separated nodes
}