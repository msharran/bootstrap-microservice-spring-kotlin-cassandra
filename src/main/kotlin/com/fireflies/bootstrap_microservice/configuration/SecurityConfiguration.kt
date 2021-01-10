package com.fireflies.bootstrap_microservice.configuration

import com.fireflies.bootstrap_microservice.security.AuthorizationFilter
import com.fireflies.bootstrap_microservice.security.JwtProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http // remove csrf and state in session because in jwt we do not need them
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and() // add jwt filters (1. authorization)
            .addFilter(AuthorizationFilter(authenticationManager()))
            .authorizeRequests() // configure access rules
            .antMatchers(*JwtProperties.AUTHORIZATION_IGNORED_ENDPOINTS).permitAll()
            .antMatchers(JwtProperties.ADMIN_ENDPOINTS).hasRole("ADMIN")
            .anyRequest().authenticated()
    }

}
