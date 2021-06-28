package com.github.alfabravo2013.readyforexams.util

import android.util.Patterns
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

const val MIN_PASSWORD_LENGTH = 9
const val MAX_PASSWORD_LENGTH = 20

fun String.isInvalidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches().not()

fun String.isInvalidPassword(): Boolean = length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending: AtomicBoolean = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    fun call() {
        value = null
    }
}

sealed class Result {
    object Success : Result()
    data class Failure(val errorMessage: String = "") : Result()
}
