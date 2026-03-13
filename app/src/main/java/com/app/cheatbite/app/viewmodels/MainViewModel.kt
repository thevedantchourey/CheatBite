package com.app.cheatbite.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.cheatbite.app.Events
import com.app.cheatbite.app.domain.entity.Sender
import com.app.cheatbite.app.domain.model.ChatMessage
import com.app.cheatbite.app.domain.model.Suggestion
import com.app.cheatbite.app.domain.model.UserProfile
import com.app.cheatbite.app.domain.usecase.ObserveCurrentUserUseCase
import com.app.cheatbite.app.domain.usecase.SnackSenseiUseCase
import com.app.cheatbite.app.domain.usecase.UserDataUseCase
import com.app.cheatbite.app.states.ChatState
import com.app.cheatbite.app.states.UserState
import com.app.cheatbite.core.domain.util.onError
import com.app.cheatbite.core.domain.util.onSuccess
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class MainViewModel(
    private val snackSenseiUseCase: SnackSenseiUseCase,
    private val userDataUseCase: UserDataUseCase,
    private val firebaseAuth: FirebaseAuth,
    observeCurrentUserUseCase: ObserveCurrentUserUseCase
): ViewModel() {

    private val _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState

    private val _suggestions = MutableStateFlow<List<Suggestion>>(emptyList())
    val suggestions: StateFlow<List<Suggestion>> = _suggestions.asStateFlow()

    private val _events = MutableSharedFlow<Events>()
//    val events = _events.asSharedFlow()

    private val _liked = MutableStateFlow(false)
    val liked = _liked.asStateFlow()

    fun updateLiked(isLiked: Boolean) {
        _liked.update { isLiked }
    }


    val user: StateFlow<UserProfile> = observeCurrentUserUseCase.invoke()

    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
//    val userState: StateFlow<UserState> = _userState.asStateFlow()

    fun snackSensei(userQuery: String) {
        if (userQuery.isBlank()) return

        _chatState.update { currentState ->
            currentState.copy(
                messages = currentState.messages + ChatMessage(text = userQuery, sender = Sender.USER),
                showFeedbackButtons = false
            )
        }

        viewModelScope.launch {
            _chatState.update { it.copy(isBotTyping = true) }
            val response = snackSenseiUseCase.invoke(userQuery, user.value)
            delay(3000)
            val botMsg = ChatMessage(
                text = response.message,
                sender = Sender.BOT,
                linkedSuggestions = response.suggestions
            )
            _suggestions.update { response.suggestions }
            _chatState.update {
                it.copy(
                    messages = it.messages + botMsg,
                    isBotTyping = false,
                    showFeedbackButtons = response.requiresFeedback
                )
            }
        }
    }

    fun getUserData(){
        _userState.update { UserState.Loading }
        val currentUserId = firebaseAuth.currentUser?.uid ?: return
        viewModelScope.launch {
            val userData = userDataUseCase.getUserById(currentUserId)
            userData.onSuccess { _, _ ->
                _userState.update { UserState.Success }
            }.onError { error, message ->
                _userState.update { UserState.Error }
                _events.emit(Events.Error(error, message ?: "Failed to update the information for the user."))
            }
        }
    }

    fun updateUserData(){
        if(liked.value){
            _userState.update { UserState.Loading }
            val currentUserId = firebaseAuth.currentUser?.uid ?: return
            viewModelScope.launch {
                val userData = userDataUseCase.updateUser(uID = currentUserId, field = "history", value = FieldValue.arrayUnion(*suggestions.value.toTypedArray()))
                userData.onSuccess { _, _ ->
                    _userState.update { UserState.Success }
                    _chatState.update {
                        it.copy(messages = it.messages + ChatMessage("Saved to history! ✅", Sender.BOT), showFeedbackButtons = false)
                    }
                }.onError { error, message ->
                    _userState.update { UserState.Error }
                    _events.emit(Events.Error(error, message))
                }
            }
        }else{
            snackSensei("suggest")
        }
    }

    fun deleteOutDatedHistory(){
        _userState.update { UserState.Loading }
        val currentUserId = firebaseAuth.currentUser?.uid ?: return
        viewModelScope.launch {
            val userData = userDataUseCase.deleteOutDatedHistory(currentUserId)
            userData.onSuccess { _, _ ->
                _userState.update { UserState.Success }
                _events.emit(Events.Success("History was cleared."))
            }.onError { error, message ->
                _userState.update { UserState.Error }
                _events.emit(Events.Error(error, message ?: "Failed to update the information for the user."))
            }
        }
    }

    init{
        if (firebaseAuth.currentUser != null) {
            getUserData()
        }
        deleteOutDatedHistory()
    }

    fun resetChat() {
        _chatState.update { ChatState() }
        _suggestions.update { emptyList() }
        _liked.update { false }
    }

}