package cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val success: Boolean,
    val token: String,
)