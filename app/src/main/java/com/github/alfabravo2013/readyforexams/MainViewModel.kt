package com.github.alfabravo2013.readyforexams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.login.UserStatusUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val userStatusUseCase: UserStatusUseCase
) : ViewModel() {

    private val _onEvent = MutableLiveData<OnEvent>()

    val onEvent : LiveData<OnEvent>
        get() = _onEvent

    fun onNavigationChanged(id: Int) {
        when(id) {
            R.id.homeFragment -> {
                _onEvent.value = OnEvent.HideBackButton
                _onEvent.value =  OnEvent.ShowLogoutButton
            }
            R.id.loginFragment -> {
                _onEvent.value = OnEvent.HideBackButton
                _onEvent.value = OnEvent.HideLogoutButton
            }
            R.id.createFragment -> _onEvent.value = OnEvent.ShowBackButton
        }
    }

    fun checkUserStatus() = viewModelScope.launch {
        val isLoggedIn = userStatusUseCase.isLoggedIn()
        if (isLoggedIn) {
            _onEvent.value = OnEvent.SetStartDestinationHome
        } else {
            _onEvent.value = OnEvent.SetStartDestinationLogin
        }
    }

    fun onLogout() = viewModelScope.launch {
        userStatusUseCase.logout()
        _onEvent.value = OnEvent.NavigateToLoginScreen
    }

    sealed class OnEvent {
        object ShowBackButton : OnEvent()
        object HideBackButton : OnEvent()
        object HideLogoutButton : OnEvent()
        object ShowLogoutButton : OnEvent()
        object NavigateToLoginScreen : OnEvent()
        object SetStartDestinationLogin : OnEvent()
        object SetStartDestinationHome : OnEvent()
    }
}
