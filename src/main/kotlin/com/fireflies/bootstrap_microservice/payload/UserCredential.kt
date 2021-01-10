package com.fireflies.bootstrap_microservice.payload

import java.util.*

data class UserCredential(
    var token: String,

    var userType: UserType,

    var userId: UUID,

    var username: String
)

enum class UserType {
    INFLUENCER, BRAND
}