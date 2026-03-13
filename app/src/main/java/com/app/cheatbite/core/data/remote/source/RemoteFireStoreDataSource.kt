package com.app.cheatbite.core.data.remote.source

import com.app.cheatbite.core.data.networking.firebaseSafeCall
import com.app.cheatbite.core.data.remote.dto.UserProfileDto
import com.app.cheatbite.core.domain.util.FirestoreCallError
import com.app.cheatbite.core.domain.util.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date


class RemoteFirestoreDataSource(
    private val firestore: FirebaseFirestore
) : FirestoreDataSource {
    override suspend fun createOrUpdateUser(user: UserProfileDto): Result<Unit, FirestoreCallError> {
        return firebaseSafeCall {
            firestore.collection("users")
                .document(user.userId!!)
                .set(user, SetOptions.merge())
                .await()
            Result.Success(Unit)
        }
    }

    override suspend fun getUser(uid: String): Result<UserProfileDto?, FirestoreCallError> {
        return firebaseSafeCall {
            val document = firestore.collection("users")
                .document(uid)
                .get()
                .await()

            document.toObject(UserProfileDto::class.java)
        }
    }

    override suspend fun updateUser(uID: String, field: String, value: Any): Result<Unit, FirestoreCallError> {
        return firebaseSafeCall {
            firestore.collection("users")
                .document(uID)
                .update(field,value)
                .await()
            Result.Success(Unit)
        }
    }

    override suspend fun updateUser(uID: String, updates: Map<String, Any>): Result<Unit, FirestoreCallError> {
        return firebaseSafeCall {
            firestore.collection("users")
                .document(uID)
                .update(updates)
                .await()
            Result.Success(Unit)
        }
    }

    override suspend fun deleteExpiredHistory(uID: String): Result<Unit, FirestoreCallError>  {
        return firebaseSafeCall {
            val sevenDaysAgo = getSevenDaysAgoDate()

            val snapshot = firestore.collection("users")
                .document(uID)
                .collection("history")
                .whereLessThan("timestamp", sevenDaysAgo)
                .get()
                .await()

            if (!snapshot.isEmpty) {
                val batch = firestore.batch()
                snapshot.documents.forEach { doc ->
                    batch.delete(doc.reference)
                }
                batch.commit().await()
            }
        }
    }

    private fun getSevenDaysAgoDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        return calendar.time
    }

}