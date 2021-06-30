package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

internal class LocalDataSourceTest : KoinTest {
    private val localDataSource by inject<LocalDataSource>()

    private val defaultEmail = "test@test.com"
    private val defaultPassword = "123456789"

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single { LocalDataSource() }
            }
        )
    }

    @Test
    @DisplayName("Given non-unique email When signup Then Result.Failure")
    fun signUpWithNonUniqueEmail() {
        val actual = localDataSource.signUp(defaultEmail, defaultPassword)
        assertTrue(actual is Result.Failure)
    }

    @Test
    @DisplayName("Given unique email When signup Then Result.Success")
    fun signUpWithUniqueEmail() {
        val uniqueEmail = "unique@test.com"
        val actual = localDataSource.signUp(uniqueEmail, defaultPassword)
        assertTrue(actual is Result.Success)
    }

    @Test
    @DisplayName("Given existing email When resetPassword Then Result.Success")
    fun resetPasswordWithExistingEmail() {
        val actual = localDataSource.resetPassword(defaultEmail)
        assertTrue(actual is Result.Success)
    }

    @Test
    @DisplayName("Given non-existing email When resetPassword Then Result.Failure")
    fun resetPasswordWithNonExistingEmail() {
        val nonExistingEmail = "doesnotexist@test.com"
        val actual = localDataSource.resetPassword(nonExistingEmail)
        assertTrue(actual is Result.Failure)
    }

    @Test
    @DisplayName("Given existing email and correct password When login Then Result.Success")
    fun loginExistingEmailAndPassword() {
        val actual = localDataSource.login(defaultEmail, defaultPassword)
        assertTrue(actual is Result.Success)
    }

    @Test
    @DisplayName("Given existing email and incorrect password When login Then Result.Failure")
    fun loginExistingEmailAndIncorrectPassword() {
        val incorrectPassword = "987654321"
        val actual = localDataSource.login(defaultEmail, incorrectPassword)
        assertTrue(actual is Result.Failure)
    }

    @Test
    @DisplayName("Given non-existing email and correct password When login Then Result.Failure")
    fun loginNonExistingEmailAndCorrectPassword() {
        val nonExistingEmail = "nonexisting@test.com"
        val actual = localDataSource.login(nonExistingEmail, defaultPassword)
        assertTrue(actual is Result.Failure)
    }

    @Test
    @DisplayName("Given non-existing email and incorrect password When login Then Result.Failure")
    fun loginNonExistingEmailAndIncorrectPassword() {
        val nonExistingEmail = "nonexisting@test.com"
        val incorrectPassword = "987654321"
        val actual = localDataSource.login(nonExistingEmail, incorrectPassword)
        assertTrue(actual is Result.Failure)
    }
}
