package com.app.cheatbite.app.states

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    object LoggedIn: AuthState()
    object Error: AuthState()
}