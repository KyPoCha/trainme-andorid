package cz.cvut.fit.poliskyr.trainmeapp.networking

import android.util.Log
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.BadGatewayException
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.ForbiddenException
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.UnauthorizedException
import cz.cvut.fit.poliskyr.trainmeapp.util.SessionManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class ApiInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken = sessionManager.getAccessToken()

        val response = chain.proceed(
            newRequestWithAccessToken(
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWQiOiIxIiwiZW1haWwiOiJhZG1pbkBtYWlsLmlvIiwidXNlcm5hbWUiOiJhZG1pbkBtYWlsLmlvIiwiaWF0IjoxNjg2MDgzNzk3LCJleHAiOjIyODYwODM3OTd9.e1_m1d6VTVVCOFQLesLiOfAqTXiWNKhIUB4cCV0PQ5W6obez0a46KmsYw0bHZ62oXh1J0HPh7oVu7h9SdL59AA",
                request
            )
        )

        if (response.code == 403) {
            throw ForbiddenException()
        } else if (response.code == 401) {
            throw UnauthorizedException()
        } else if (response.code == 502) {
            throw BadGatewayException()
        }

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val newAccessToken = sessionManager.getAccessToken()
            Log.d("TOKEN:", "New access token here")
            return if (newAccessToken != accessToken) {
                chain.proceed(newRequestWithAccessToken(accessToken, request))
            } else{
                chain.proceed(newRequestWithAccessToken(sessionManager.getAccessToken(),request))
            }
        }

        return response
    }

    private fun newRequestWithAccessToken(token: String?, request: Request): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

}