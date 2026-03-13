package com.app.cheatbite.app.domain.usecase

import com.app.cheatbite.app.domain.model.UserProfile
import com.app.cheatbite.app.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.StateFlow

class ObserveCurrentUserUseCase(private val repository: UserDataRepository) {
    operator fun invoke(): StateFlow<UserProfile> {
        return repository.currentUserProfile
    }
}