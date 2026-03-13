package com.app.cheatbite.core.data.local.dto

import kotlinx.serialization.Serializable

@Serializable
data class IntentItemsDto(
    val tag: String,
    val patterns: List<String>,
    val responses: List<String>
)
