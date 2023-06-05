package cz.cvut.fit.poliskyr.trainmeapp.model

data class User(
    val id: Int = 0,
    val username: String = "",
    val dateOfBirthday: String? = null,
    val membershipFrom: String?= null,
    val membershipTo: String? = null,
    val memberships: List<String> = listOf()
)
