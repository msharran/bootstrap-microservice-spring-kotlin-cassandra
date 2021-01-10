package com.fireflies.bootstrap_microservice.controller.v1

import com.fireflies.bootstrap_microservice.AppProperties
import com.fireflies.bootstrap_microservice.payload.UserCredential
import com.fireflies.bootstrap_microservice.response.APIResponse
import com.fireflies.bootstrap_microservice.response.responseOf
import com.fireflies.bootstrap_microservice.security.UserPrincipal
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(AppProperties.BASE_URL)
class BootstrapController {

    @GetMapping("/me")
    suspend fun getUser(@RequestParam(required = true) username: String): ResponseEntity<APIResponse<UserCredential?>> = coroutineScope {
        val user = (SecurityContextHolder.getContext().authentication.principal as? UserPrincipal)?.user
        responseOf(HttpStatus.OK) {
            user
        }
    }
}