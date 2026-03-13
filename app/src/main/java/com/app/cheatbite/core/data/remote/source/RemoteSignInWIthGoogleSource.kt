package com.app.cheatbite.core.data.remote.source


import android.app.Activity
import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.app.cheatbite.core.domain.util.AuthCallError
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import com.app.cheatbite.core.domain.util.Result


class RemoteSignInWithGoogleDataSource(
    private val firebaseAuth: FirebaseAuth,
    context: Context,
    private val webClientId: String
): SignInWithGoogleDataSource {
    private val credentialManager = CredentialManager.create(context)
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override suspend fun fetchGoogleCredential(activity: Activity): Result<Credential, AuthCallError> {
        Log.d("clientId",webClientId)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try{
            val result = credentialManager.getCredential(activity, request).credential
            Result.Success(result)
        }catch (e: GetCredentialException){
            Log.e("AuthDebug", "Full crash trace in signInWithGoogle:", e)
            Result.Error(AuthCallError.SIGN_IN_FAILED, e.message)
        }catch (e: Exception){
            Log.e("AuthDebug", "Full crash trace in signInWithGoogle:", e)
            Result.Error(AuthCallError.UNKNOWN, e.message)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser, AuthCallError> {
        return try{
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val result = authResult.user
                ?: throw IllegalStateException("User is null after Google Sign-In")
            Result.Success(result)
        }catch(e: Exception) {
            Result.Error(AuthCallError.SIGN_IN_FAILED, e.message)
        }
    }


    override suspend fun signOut(): Result<Unit, AuthCallError> {
        return try {
            firebaseAuth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            Result.Success(Unit)
        }catch(e: Exception){
            Result.Error(AuthCallError.SIGN_OUT_FAILED, e.message)
        }
    }
}