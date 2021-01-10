package com.fireflies.bootstrap_microservice.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fireflies.bootstrap_microservice.AppProperties
import com.fireflies.bootstrap_microservice.service_client.AuthServiceClient
import com.fireflies.bootstrap_microservice.payload.UserCredential
import com.fireflies.bootstrap_microservice.util.log
import com.fireflies.bootstrap_microservice.util.objectMapper
import kotlinx.coroutines.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(authenticationManager: AuthenticationManager): BasicAuthenticationFilter(authenticationManager) {

    @Throws(Exception::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(AppProperties.Security.AUTHORIZATION)
        if (header == null || !header.startsWith(AppProperties.Security.BEARER_)) {
            chain.doFilter(request, response)
            return
        }
        val authentication = runBlocking {
            getUsernamePasswordAuthentication(request)
        }
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    @Throws(Exception::class)
    private suspend fun getUsernamePasswordAuthentication(request: HttpServletRequest): Authentication {
        val token = request.getHeader(AppProperties.Security.AUTHORIZATION).replace(AppProperties.Security.BEARER_, "")
        val username = JWT.require(Algorithm.HMAC512(AppProperties.Security.SECRET.toByteArray()))
            .build()
            .verify(token)
            .subject
        val user: UserCredential = AuthServiceClient.verifyUser(token, username)
        val principal = UserPrincipal(user)
        return UsernamePasswordAuthenticationToken(principal, null, principal.authorities)
    }
}