package com.app.cheatbite.core.data.remote.source

import com.app.cheatbite.core.data.remote.dto.UserProfileDto
import com.app.cheatbite.core.domain.util.FirestoreCallError
import com.app.cheatbite.core.domain.util.Result

interface FirestoreDataSource {
    suspend fun createOrUpdateUser(user: UserProfileDto): Result<Unit, FirestoreCallError>
    suspend fun getUser(uid: String): Result<UserProfileDto?, FirestoreCallError>
    suspend fun updateUser(uID: String, field: String, value: Any): Result<Unit, FirestoreCallError>
    suspend fun updateUser(uID: String, updates: Map<String, Any>): Result<Unit, FirestoreCallError>
    suspend fun deleteExpiredHistory(uID: String): Result<Unit, FirestoreCallError>
}