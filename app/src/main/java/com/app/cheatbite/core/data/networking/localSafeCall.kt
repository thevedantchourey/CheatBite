package com.app.cheatbite.core.data.networking

import com.app.cheatbite.core.domain.util.FirestoreCallError
import com.app.cheatbite.core.domain.util.ParseCallError
import com.app.cheatbite.core.domain.util.Result
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.serialization.SerializationException
import java.io.IOException


inline fun <T> localSafeCall(parseCall: () -> T): Result<T, ParseCallError> {
     return try {
         Result.Success(parseCall())
     } catch (e: SerializationException) {
         Result.Error(ParseCallError.SERIALIZATION, e.message ?: "Failed to parse JSON")
     } catch (e: Exception) {
         Result.Error(ParseCallError.UNKNOWN, e.message ?: "An unknown error occurred")
     }
}

suspend inline fun <T> firebaseSafeCall(crossinline call: suspend () -> T): Result<T, FirestoreCallError> {
    return try {
        Result.Success(call())
    } catch (e: SerializationException) {
        Result.Error(FirestoreCallError.SERIALIZATION, e.message ?: "Failed to parse Firestore data")
    } catch (e: FirebaseFirestoreException) {
        val error = when (e.code) {
            FirebaseFirestoreException.Code.PERMISSION_DENIED -> FirestoreCallError.PERMISSION_DENIED
            FirebaseFirestoreException.Code.UNAVAILABLE -> FirestoreCallError.DOCUMENT_NOT_FOUND
            FirebaseFirestoreException.Code.UNAUTHENTICATED -> FirestoreCallError.UNAUTHENTICATED
            else -> FirestoreCallError.WRITE_FAILED
        }
        Result.Error(error, e.message ?: e.toString())
    } catch (e: IOException) {
        Result.Error(FirestoreCallError.WRITE_FAILED, e.message ?: e.toString())
    } catch (e: Exception) {
        Result.Error(FirestoreCallError.UNKNOWN, e.message ?: e.toString())
    }
}