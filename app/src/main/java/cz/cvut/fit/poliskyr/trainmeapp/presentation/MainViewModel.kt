package cz.cvut.fit.poliskyr.trainmeapp.presentation

import androidx.lifecycle.ViewModel
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignInRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {
    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token

    fun signIn(signInRequest: SignInRequest){

    }
}