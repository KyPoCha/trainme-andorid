package cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest (
    val email: String,
    val username: String,
    val first_name: String,
    val last_name: String,
    val password: String,
    val confirmPassword: String,
)