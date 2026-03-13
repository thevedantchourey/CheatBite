package com.app.cheatbite.app.domain.model

data class SnackSenseiResponse(
    val message: String,
    val suggestions: List<Suggestion> = emptyList(),
    val requiresFeedback: Boolean = false
)