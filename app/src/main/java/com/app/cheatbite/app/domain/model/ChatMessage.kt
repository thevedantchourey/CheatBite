package com.app.cheatbite.app.domain.model

import com.app.cheatbite.app.domain.entity.Sender

data class ChatMessage(
    val text: String,
    val sender: Sender,
    val linkedSuggestions: List<Suggestion> = emptyList()
)