package com.github.alfabravo2013.readyforexams.domain.login

class UserStatusUseCase(private val loginRepository: LoginRepository) {
    suspend fun isLoggedIn() = loginRepository.isLoggedIn()

    suspend fun logout() = loginRepository.logout()
}
