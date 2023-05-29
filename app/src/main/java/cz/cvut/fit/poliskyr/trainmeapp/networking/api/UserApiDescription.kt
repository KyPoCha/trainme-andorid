package cz.cvut.fit.poliskyr.trainmeapp.networking.api

import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response.UserResponse
import retrofit2.http.GET

interface UserApiDescription {
    @GET("user")
    suspend fun getUserInfo(): UserResponse

}