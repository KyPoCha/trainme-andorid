package cz.cvut.fit.poliskyr.trainmeapp.networking.api

import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignInRequest
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignUpRequest
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response.SignInResponse
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiDescription {
    @POST("auth/signin")
    suspend fun signInSystem(@Body signInRequest: SignInRequest): SignInResponse

    @POST("auth/signup")
    suspend fun signUpSystem(@Body signUpRequest: SignUpRequest): SignUpResponse
}