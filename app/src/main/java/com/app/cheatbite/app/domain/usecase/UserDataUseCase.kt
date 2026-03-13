package com.app.cheatbite.app.domain.usecase

import com.app.cheatbite.app.domain.model.UserProfile
import com.app.cheatbite.app.domain.repository.UserDataRepository
import com.app.cheatbite.core.domain.util.FirestoreCallError
import com.app.cheatbite.core.domain.util.Result


class UserDataUseCase(
    private val userRepository: UserDataRepository,
) {

    suspend fun createUser(user: UserProfile): Result<Unit, FirestoreCallError> {
        return userRepository.createOrUpdateUser(user = user)
    }

    suspend fun getUserById(uid: String): Result<UserProfile?, FirestoreCallError> {
        return userRepository.getUser(uid = uid)
    }

    suspend fun updateUser(uID: String, field: String, value: Any): Result<Unit, FirestoreCallError> {
        return userRepository.updateUser(uID = uID, field = field, value = value)
    }

    suspend fun updateUser(uID: String, updates: Map<String, Any>): Result<Unit, FirestoreCallError> {
        return userRepository.updateUser(uID = uID, updates = updates)
    }

    suspend fun deleteOutDatedHistory(uID: String): Result<Unit, FirestoreCallError> {
        return userRepository.deleteOutDatedHistory(uID = uID)
    }


}
