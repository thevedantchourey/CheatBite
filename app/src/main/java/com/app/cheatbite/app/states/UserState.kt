package com.app.cheatbite.app.states

sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    object Success : UserState()
    object Error: UserState()
}