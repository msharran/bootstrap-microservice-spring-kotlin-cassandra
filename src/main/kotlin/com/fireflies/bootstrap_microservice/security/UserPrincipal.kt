package com.fireflies.bootstrap_microservice.security

import com.fireflies.bootstrap_microservice.payload.UserCredential
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


class UserPrincipal(val user: UserCredential) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return arrayListOf()
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    val userType: String
        get() = user.userType.name

    val userId: UUID
        get() = user.userId
}