package com.github.alfabravo2013.readyforexams.presentation.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.create.SaveChangesUseCase
import kotlinx.coroutines.launch

class SaveChangesDialogViewModel(
    private val saveChangesUseCase: SaveChangesUseCase
) : ViewModel() {

    private val _onEvent = MutableLiveData<OnEvent>()
    val onEvent: LiveData<OnEvent> get() = _onEvent

    fun onPositiveButtonClick() = viewModelScope.launch {
        saveChangesUseCase.saveChanges()
        _onEvent.value = OnEvent.NavigateToHomeScreen
        Log.d("@#$", "onPositiveButtonClick: navigate to home screen dispatched")
    }

    fun onNegativeButtonClick() = viewModelScope.launch {
        saveChangesUseCase.discardChanges()
        _onEvent.value = OnEvent.NavigateToHomeScreen
        Log.d("@#$", "onNegativeButtonClick: navigate to home screen dispatched")
    }

    sealed class OnEvent {
        object NavigateToHomeScreen : OnEvent()
    }
}
