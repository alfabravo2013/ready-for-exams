package com.github.alfabravo2013.readyforexams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _onEvent = MutableLiveData<OnEvent>()

    val onEvent : LiveData<OnEvent>
        get() = _onEvent

    fun onNavigationChanged(id: Int) {
        when(id) {
            R.id.homeFragment -> _onEvent.value = OnEvent.HideBackButton
            R.id.createFragment -> _onEvent.value = OnEvent.ShowBackButton
        }
    }

    sealed class OnEvent {
        object ShowBackButton : OnEvent()
        object HideBackButton : OnEvent()
    }
}
