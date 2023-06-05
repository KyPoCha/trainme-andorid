package cz.cvut.fit.poliskyr.trainmeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.poliskyr.trainmeapp.data.source.UserDataSource
import cz.cvut.fit.poliskyr.trainmeapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userDataSource: UserDataSource) : ViewModel() {
    private val _user = MutableStateFlow<User>(User())
    val user: StateFlow<User> = _user

    init {
        viewModelScope.launch {
            _user.value = userDataSource.getUser()
        }
    }
}