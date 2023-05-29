package cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response

import kotlinx.serialization.Serializable

@Serializable
data class TrainerResponse(
    val id: Int,
    val username: String,
    val reviewValue: Int,
    val email: String,
    val telephone: String,
    val priceForOneTraining: String,
    val priceForMonthTraining: String,
    val dateOfBirthday: String
)
