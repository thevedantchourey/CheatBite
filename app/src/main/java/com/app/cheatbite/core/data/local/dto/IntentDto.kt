package com.app.cheatbite.core.data.local.dto

import kotlinx.serialization.Serializable

@Serializable
data class IntentDto(
    val intents: List<IntentItemsDto>
)