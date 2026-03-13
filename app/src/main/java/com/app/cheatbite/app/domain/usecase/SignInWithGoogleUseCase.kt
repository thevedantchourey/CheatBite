package com.app.cheatbite.app.domain.usecase

import android.app.Activity
import androidx.credentials.Credential
import com.app.cheatbite.app.domain.repository.AuthRepository
import com.app.cheatbite.core.domain.util.AuthCallError
import com.google.firebase.auth.FirebaseUser
import com.app.cheatbite.core.domain.util.Result


class SignInWithGoogleUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun fetchGoogleCredential(activity: Activity): Result<Credential, AuthCallError> {
        return authRepository.fetchGoogleCredential(activity)
    }
    suspend operator fun invoke(idToken: String): Result<FirebaseUser, AuthCallError> {
        return authRepository.signInWithGoogle(idToken)
    }
    suspend fun signOut(): Result<Unit, AuthCallError> {
        return authRepository.signOut()
    }
}
