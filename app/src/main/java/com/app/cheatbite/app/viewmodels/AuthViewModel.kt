package com.app.cheatbite.app.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.cheatbite.UserSharedPreference
import com.app.cheatbite.app.Events
import com.app.cheatbite.app.domain.model.UserProfile
import com.app.cheatbite.app.domain.model.WeekDiet
import com.app.cheatbite.app.domain.usecase.SignInWithGoogleUseCase
import com.app.cheatbite.app.domain.usecase.UserDataUseCase
import com.app.cheatbite.app.states.AuthState
import com.app.cheatbite.core.domain.util.FirestoreCallError
import com.app.cheatbite.core.domain.util.onError
import com.app.cheatbite.core.domain.util.onSuccess
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AuthViewModel(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signedInUserDataUseCase: UserDataUseCase,
    private val preferencesManager: UserSharedPreference,
) : ViewModel() {


    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    private val _events = MutableSharedFlow<Events>()
    val events = _events.asSharedFlow()


    fun signInWithGoogle(activity: Activity) {
        viewModelScope.launch {
            try {
                val credential = signInWithGoogleUseCase.fetchGoogleCredential(activity)
                credential.onSuccess { credential, _ ->
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken
                    val signInResult = signInWithGoogleUseCase.invoke(idToken)
                    signInResult.onSuccess { signInResult, _ ->
                        val firebaseUser = signInResult
                        val uid = firebaseUser.uid
                        val email = firebaseUser.email ?: ""
                        val name = firebaseUser.displayName ?: ""
                        val userResult = signedInUserDataUseCase.getUserById(uid)
                        userResult.onSuccess { user, _ ->
                            if (user != null) {
                                _authState.update { AuthState.LoggedIn }
                                preferencesManager.saveUserStatus(loggedIn = true)
                            } else {
                                val user = UserProfile(userId = uid, avatar = "base_profile", email = email, username = name, subscription = "free", weeklyDiet = WeekDiet(), mealType = "veg")
                                createUser(userProfile = user)
                            }
                        }.onError { error, message ->
                            _authState.update { AuthState.Error }
                            _events.emit(Events.Error(error, message ?: "Failed to fetch user"))
                        }
                    }.onError { error, message ->
                        _authState.update { AuthState.Error }
                        _events.emit(Events.Error(error, message ?: "Google sign-in failed"))
                    }
                }.onError { error, message ->
                    _authState.update { AuthState.Error }
                    _events.emit(Events.Error(error, message ?: "Google sign-in failed"))
                }
            } catch (e: Exception) {
                _authState.update { AuthState.Error }
                _events.emit(Events.Error(FirestoreCallError.UNKNOWN, e.message ?: "Unknown error"))
            }
        }
    }

    fun createUser(userProfile: UserProfile){
        viewModelScope.launch {
            val createUser = signedInUserDataUseCase.createUser(user = userProfile)
            createUser.onSuccess { _, _ ->
                _authState.update { AuthState.Success }
                _events.emit(Events.Success("User created! Enjoy your stay."))
            }.onError { error, message ->
                _authState.update { AuthState.Error }
                _events.emit(Events.Error(error, message ?: "Failed to create user."))
            }
        }
    }

    fun resetAuthState(){
        _authState.update { AuthState.Idle }
    }
}