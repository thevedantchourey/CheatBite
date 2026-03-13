package com.app.cheatbite.app.states

import com.app.cheatbite.app.domain.model.UserProfile

sealed class UserDataState {
    object Idle : UserDataState()
    object Loading : UserDataState()
    data class Success(val user: UserProfile) : UserDataState()
}