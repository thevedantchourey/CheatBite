package com.app.cheatbite.app.domain.repository

import android.app.Activity
import androidx.credentials.Credential
import com.app.cheatbite.core.domain.util.AuthCallError
import com.google.firebase.auth.FirebaseUser
import com.app.cheatbite.core.domain.util.Result


interface AuthRepository {

    // --- Google Authentication ---
    suspend fun fetchGoogleCredential(activity: Activity): Result<Credential, AuthCallError>
    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser, AuthCallError>
    suspend fun signOut(): Result<Unit, AuthCallError>
}