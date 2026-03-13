package com.app.cheatbite.app.states

import com.app.cheatbite.app.domain.model.ChatMessage

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val isBotTyping: Boolean = false,
    val showFeedbackButtons: Boolean = false
)