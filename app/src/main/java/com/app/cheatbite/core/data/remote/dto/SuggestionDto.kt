package com.app.cheatbite.core.data.remote.dto

import com.app.cheatbite.core.data.local.dto.FoodItemDto
import kotlinx.serialization.Serializable

@Serializable
data class SuggestionDto(
    val food: FoodItemDto = FoodItemDto(),
    val matchScore: Int = 0
)