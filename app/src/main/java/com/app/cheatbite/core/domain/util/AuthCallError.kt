package com.app.cheatbite.core.domain.util

enum class AuthCallError : CallError {
    CREDENTIAL_FETCH_FAILED,
    SIGN_IN_FAILED,
    SIGN_OUT_FAILED,
    USER_NULL,
    UNKNOWN
}