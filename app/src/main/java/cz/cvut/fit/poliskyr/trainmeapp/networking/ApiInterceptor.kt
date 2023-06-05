package cz.cvut.fit.poliskyr.trainmeapp.networking

import android.util.Log
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
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMyIsImlkIjoiMzMiLCJlbWFpbCI6ImtpcmlsbHBvbGlzaGNodWswMkBnbWFpbC5jb20iLCJ1c2VybmFtZSI6ImtpcmlsbHBvbGlzaGNodWswMkBnbWFpbC5jb20iLCJpYXQiOjE2ODU5ODk1MzAsImV4cCI6MjI4NTk4OTUzMH0.TWFOKcJDUKKuDqZeZQsHbgF_5Znhg8mhJ76sQZzDhQT2BF_w7nEJljBWVLBU5sKrZLAem_svg-EyiH7MfWdqyw",
                request
            )
        )

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