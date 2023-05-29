package cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val uri: String,
    val imageBytes: String,
    val trainerId: Int
)
