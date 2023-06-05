package cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val username: String,
    val dateOfBirthday: String?,
    val membershipFrom: String?,
    val membershipTo: String?,
    val memberships: List<String>
)
