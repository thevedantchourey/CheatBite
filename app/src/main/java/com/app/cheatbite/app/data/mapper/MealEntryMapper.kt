package com.app.cheatbite.app.data.mapper

import com.app.cheatbite.app.domain.model.MealEntry
import com.app.cheatbite.core.data.remote.dto.MealEntryDto


fun MealEntryDto.toMealEntry(): MealEntry {
    return MealEntry(
        time = time,
        food = food
    )
}

fun MealEntry.toMealEntryDto(): MealEntryDto {
    return MealEntryDto(
        time = time,
        food = food
    )
}