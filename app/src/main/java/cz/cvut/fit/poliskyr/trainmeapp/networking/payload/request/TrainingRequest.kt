package cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request

import kotlinx.serialization.Serializable

@Serializable
data class TrainingRequest(
    val trainerId: Int,
    val place: String,
    val timeFrom: String,
    val timeTo: String
)
