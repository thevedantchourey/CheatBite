package com.app.cheatbite.app.domain.model

data class IntentItems(
    val tag: String,
    val patterns: List<String>,
    val responses: List<String>
)
