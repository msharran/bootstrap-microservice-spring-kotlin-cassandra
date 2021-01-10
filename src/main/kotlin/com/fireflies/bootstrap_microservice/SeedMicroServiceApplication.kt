package com.fireflies.bootstrap_microservice

import com.fireflies.bootstrap_microservice.util.context
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SeedMicroServiceApplication

fun main(args: Array<String>) {
	context = runApplication<SeedMicroServiceApplication>(*args)
}