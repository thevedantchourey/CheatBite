package com.app.cheatbite.app.domain.repository

import com.app.cheatbite.app.domain.model.UserProfile
import com.app.cheatbite.core.domain.util.FirestoreCallError
import com.app.cheatbite.core.domain.util.Result
import kotlinx.coroutines.flow.StateFlow


interface UserDataRepository{
    suspend fun createOrUpdateUser(user: UserProfile): Result<Unit, FirestoreCallError>
    suspend fun getUser(uid: String): Result<UserProfile?, FirestoreCallError>
    suspend fun  updateUser(uID: String, field: String, value: Any): Result<Unit, FirestoreCallError>
    suspend fun  updateUser(uID: String, updates: Map<String, Any>): Result<Unit, FirestoreCallError>
    suspend fun deleteOutDatedHistory(uID: String): Result<Unit, FirestoreCallError>

     val currentUserProfile: StateFlow<UserProfile>

}