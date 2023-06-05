package cz.cvut.fit.poliskyr.trainmeapp.util

import android.util.Log
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val pref: AppSharedPreferences,
) {
    fun getAccessToken(): String? = pref.getAccessToken()

    fun setAccessToken(token: String) {
        Log.d("SET TOKEN SESSION MANAGER", token.split(" ")[1])
        pref.setAccessToken(token.split(" ")[1])
    }

    fun logout() {
        pref.setAccessToken("")
    }
}