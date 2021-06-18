package com.github.alfabravo2013.readyforexams.util

import android.util.Patterns

const val MIN_PASSWORD_LENGTH = 9
const val MAX_PASSWORD_LENGTH = 20

fun String.isInvalidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches().not()

fun String.isInvalidPassword(): Boolean = length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH

open class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}