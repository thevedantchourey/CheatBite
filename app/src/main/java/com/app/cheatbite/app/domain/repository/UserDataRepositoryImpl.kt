package com.app.cheatbite.app.domain.repository

import android.content.Context
import com.app.cheatbite.app.data.mapper.toUserProfile
import com.app.cheatbite.app.data.mapper.toUserProfileDto
import com.app.cheatbite.app.domain.model.UserProfile
import com.app.cheatbite.core.data.remote.dto.UserProfileDto
import com.app.cheatbite.core.data.remote.source.RemoteFirestoreDataSource
import com.app.cheatbite.core.domain.util.FirestoreCallError
import com.app.cheatbite.core.domain.util.Result
import com.app.cheatbite.core.domain.util.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class UserDataRepositoryImpl(
    private val remoteFirestoreDataSource: RemoteFirestoreDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val context: Context
): UserDataRepository {

    private val _currentUserProfile = MutableStateFlow(UserProfile())
    override val currentUserProfile = _currentUserProfile.asStateFlow()

    override suspend fun createOrUpdateUser(user: UserProfile): Result<Unit, FirestoreCallError> {
        return withContext(defaultDispatcher){
            val dtoResult: Result<Unit, FirestoreCallError> = remoteFirestoreDataSource.createOrUpdateUser(user = user.toUserProfileDto())
            dtoResult.map { response -> response }
        }
    }

    override suspend fun getUser(uid: String): Result<UserProfile?, FirestoreCallError> {
        return withContext(defaultDispatcher){
            val dtoResult: Result<UserProfileDto?, FirestoreCallError> = remoteFirestoreDataSource.getUser(uid = uid)
            val finalResult = dtoResult.map { response -> response?.toUserProfile() }

            if (finalResult is Result.Success) {
                _currentUserProfile.update { finalResult.data?: UserProfile() }
            }

            finalResult
        }
    }

    override suspend fun updateUser(
        uID: String,
        field: String,
        value: Any
    ): Result<Unit, FirestoreCallError> {
        return withContext(defaultDispatcher){
            val dtoResult:  Result<Unit, FirestoreCallError> = remoteFirestoreDataSource.updateUser(uID = uID, field = field, value = value)
            dtoResult.map { response -> response }
        }
    }

    override suspend fun updateUser(
        uID: String,
        updates: Map<String, Any>
    ): Result<Unit, FirestoreCallError> {
        return withContext(defaultDispatcher){
            val dtoResult:  Result<Unit, FirestoreCallError> = remoteFirestoreDataSource.updateUser(uID = uID, updates = updates)
            dtoResult.map { response -> response }
        }
    }

    override suspend fun deleteOutDatedHistory(uID: String): Result<Unit, FirestoreCallError> {
        val prefs = context.getSharedPreferences("cheat_bite_prefs", Context.MODE_PRIVATE)

        val lastRun = prefs.getLong("last_cleanup", 0L)
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastRun < 86400000) {
            return Result.Error(FirestoreCallError.WRITE_FAILED, "History was cleared once already today.")
        }
        return withContext(defaultDispatcher){
            val dtoResult:  Result<Unit, FirestoreCallError> = remoteFirestoreDataSource.deleteExpiredHistory(uID = uID)
            if (dtoResult is Result.Success) {
                prefs.edit { putLong("last_cleanup", currentTime) }
            }
            dtoResult.map { response -> response }
        }
    }

}