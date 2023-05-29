package cz.cvut.fit.poliskyr.trainmeapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.poliskyr.trainmeapp.data.source.AuthDataSource
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignInRequest
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignUpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token


    fun signIn(signInRequest: SignInRequest){
        viewModelScope.launch {
            _token.value = AuthDataSource.signIn(signInRequest)
            Log.d("TOKEN", _token.value)
        }
    }

    fun signUp(signUpRequest: SignUpRequest){
        viewModelScope.launch{
            AuthDataSource.signUp(signUpRequest)
        }
    }
}