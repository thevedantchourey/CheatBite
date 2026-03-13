package com.app.cheatbite.app.data.mapper

import com.app.cheatbite.app.domain.model.WeekDiet
import com.app.cheatbite.core.data.remote.dto.WeekDietDto

fun WeekDietDto.toWeekDiet(): WeekDiet {
    return WeekDiet(
        monday = monday.map { it.toMealEntry() },
        tuesday = tuesday.map { it.toMealEntry() },
        wednesday = wednesday.map { it.toMealEntry() },
        thursday = thursday.map { it.toMealEntry() },
        friday = friday.map { it.toMealEntry() },
        saturday = saturday.map { it.toMealEntry() },
        sunday = sunday.map { it.toMealEntry() }
    )
}


fun WeekDiet.toWeekDietDto(): WeekDietDto {
    return WeekDietDto(
        monday = monday.map { it.toMealEntryDto() },
        tuesday = tuesday.map { it.toMealEntryDto() },
        wednesday = wednesday.map { it.toMealEntryDto() },
        thursday = thursday.map { it.toMealEntryDto() },
        friday = friday.map { it.toMealEntryDto() },
        saturday = saturday.map { it.toMealEntryDto() },
        sunday = sunday.map { it.toMealEntryDto() }
    )
}