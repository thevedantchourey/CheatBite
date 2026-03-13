package com.app.cheatbite.app.domain.repository


import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.credentials.Credential
import com.app.cheatbite.core.data.remote.source.RemoteSignInWithGoogleDataSource
import com.app.cheatbite.core.domain.util.AuthCallError
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.app.cheatbite.core.domain.util.Result
import com.app.cheatbite.core.domain.util.map


class AuthRepositoryImpl(
    private val remoteSignInWithGoogleDataSource: RemoteSignInWithGoogleDataSource,
    private val defaultDispatcher: CoroutineDispatcher,
): AuthRepository {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override suspend fun fetchGoogleCredential(activity: Activity):  Result<Credential, AuthCallError> {
        return withContext(defaultDispatcher) {
            remoteSignInWithGoogleDataSource.fetchGoogleCredential(activity = activity)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser, AuthCallError> {
        return withContext(defaultDispatcher){
            remoteSignInWithGoogleDataSource.signInWithGoogle(idToken)
        }
    }

    override suspend fun signOut(): Result<Unit, AuthCallError> {
        return withContext(defaultDispatcher){
            val dtoResult: Result<Unit, AuthCallError> = remoteSignInWithGoogleDataSource.signOut()
            dtoResult.map { response -> response }
        }
    }

}