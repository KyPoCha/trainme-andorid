package cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest (
    val username: String,
    val password: String,
)