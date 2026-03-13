package com.app.cheatbite.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeekDietDto(
    val monday: List<MealEntryDto> = emptyList(),
    val tuesday: List<MealEntryDto> = emptyList(),
    val wednesday: List<MealEntryDto> = emptyList(),
    val thursday: List<MealEntryDto> = emptyList(),
    val friday: List<MealEntryDto> = emptyList(),
    val saturday: List<MealEntryDto> = emptyList(),
    val sunday: List<MealEntryDto> = emptyList(),
)


