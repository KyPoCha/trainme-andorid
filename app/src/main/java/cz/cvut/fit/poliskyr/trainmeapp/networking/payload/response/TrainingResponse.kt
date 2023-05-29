package cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response

import kotlinx.serialization.Serializable

@Serializable
data class TrainingResponse(
    val id: Int,
    val place: String,
    val timeTo: String,
    val timeFrom: String,
    val trainerUsername: String
)
