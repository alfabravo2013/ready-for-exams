package com.github.alfabravo2013.readyforexams.util

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse

internal class UtilsKtTest {

    @Nested
    @DisplayName("When isInvalidEmail")
    inner class EmailTest {

        @Test
        @DisplayName("Given empty string Then true")
        fun emptyString() {
            val string = ""

            val actual = string.isInvalidEmail()

            assertTrue(actual)
        }

        @Test
        @DisplayName("Given string == invalid email format Then true")
        fun invalidEmailString() {
            val string = "invalid.com"

            val actual = string.isInvalidEmail()

            assertTrue(actual)
        }

        @Test
        @DisplayName("Given string == valid email format Then false")
        fun validEmailString() {
            val string = "test@test.com"

            val actual = string.isInvalidEmail()

            assertFalse(actual)
        }
    }

    @Nested
    @DisplayName("When isInvalidPassword")
    inner class PasswordTest {

        @Test
        @DisplayName("Given too short password Then true")
        fun shortPassword() {
            val password = "12345678"

            val actual = password.isInvalidPassword()

            assertTrue(password.length < MIN_PASSWORD_LENGTH)
            assertTrue(actual)
        }

        @Test
        @DisplayName("Given too long password Then true")
        fun longPassword() {
            val password = "1234567891011121314150"

            val actual = password.isInvalidPassword()

            assertTrue(password.length > MAX_PASSWORD_LENGTH)
            assertTrue(actual)
        }

        @Test
        @DisplayName("Given password.length is within acceptable range Then true")
        fun acceptablePassword() {
            val password = "12345678910"

            val actual = password.isInvalidPassword()

            assertTrue(password.length >= MIN_PASSWORD_LENGTH)
            assertTrue(password.length <= MAX_PASSWORD_LENGTH)
            assertFalse(actual)
        }
    }
}