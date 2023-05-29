package cz.cvut.fit.poliskyr.trainmeapp.model

data class User(
    val id: Int = 0,
    val username: String = "",
    val dateOfBirthday: String = "",
    val membershipFrom: String = "",
    val membershipTo: String = "",
    val memberships: List<String> = listOf()
)
