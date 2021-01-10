package com.fireflies.bootstrap_microservice.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fireflies.bootstrap_microservice.payload.UserCredential
import com.fireflies.bootstrap_microservice.response.UnAuthorizedException
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.reactive.function.client.createExceptionAndAwait
import java.net.URI
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(authenticationManager: AuthenticationManager): BasicAuthenticationFilter(authenticationManager) {

    @Throws(Exception::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(JwtProperties.AUTHORIZATION)
        if (header == null || !header.startsWith(JwtProperties.BEARER_)) {
            chain.doFilter(request, response)
            return
        }
        val authentication = getUsernamePasswordAuthentication(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun getUsernamePasswordAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(JwtProperties.AUTHORIZATION).replace(JwtProperties.BEARER_, "")
        val username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.toByteArray()))
            .build()
            .verify(token)
            .subject
        if (username != null) {
            val user: UserCredential = runBlocking {
                try {
                    WebClient.create()
                        .get()
                        .uri(URI.create("http://localhost:8080/api/auth/login?username=$username"))
                        .accept(MediaType.APPLICATION_JSON)
                        .awaitExchange {
                            if (it.statusCode() != HttpStatus.OK)
                                throw Exception(it.createExceptionAndAwait().localizedMessage)
                            else
                                it.awaitBody()
                        }
                } catch (e: Exception) {
                    return@runBlocking null
                }
            } ?: return null
            val principal = UserPrincipal(user)
            return UsernamePasswordAuthenticationToken(principal, null, principal.authorities)
        }
        return null
    }
}