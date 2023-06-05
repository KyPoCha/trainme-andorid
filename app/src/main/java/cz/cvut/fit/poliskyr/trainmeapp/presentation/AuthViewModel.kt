package cz.cvut.fit.poliskyr.trainmeapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.poliskyr.trainmeapp.data.source.AuthDataSource
import cz.cvut.fit.poliskyr.trainmeapp.networking.ApiInterceptor
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignInRequest
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignUpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authDataSource: AuthDataSource) : ViewModel() {


    fun signIn(signInRequest: SignInRequest){
        viewModelScope.launch {
            authDataSource.signIn(signInRequest)
        }
    }

    fun signUp(signUpRequest: SignUpRequest){
        viewModelScope.launch{
            authDataSource.signUp(signUpRequest)
        }
    }
}