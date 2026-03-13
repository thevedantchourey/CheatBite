package com.app.cheatbite.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    val userId: String? = null,
    val avatar: String? = null,
    val username: String? = null,
    val mealType: String? = null,
    val email: String? = null,
    val subscription: String? = null,
    val weeklyDiet: WeekDietDto? = WeekDietDto(),
    val history:  List<SuggestionDto> = emptyList()
)