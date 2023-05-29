package cz.cvut.fit.poliskyr.trainmeapp.networking

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiInterceptor @Inject constructor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newChain = chain.request()
            .newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newChain)
    }
}