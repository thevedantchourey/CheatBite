package com.app.cheatbite.app.domain.model


data class WeekDiet(
    val monday: List<MealEntry> = emptyList(),
    val tuesday: List<MealEntry> = emptyList(),
    val wednesday: List<MealEntry> = emptyList(),
    val thursday: List<MealEntry> = emptyList(),
    val friday: List<MealEntry> = emptyList(),
    val saturday: List<MealEntry> = emptyList(),
    val sunday: List<MealEntry> = emptyList(),
)

fun WeekDiet.isNotEmpty(): Boolean{
    return this.monday.isNotEmpty() || this.tuesday.isNotEmpty() || this.wednesday.isNotEmpty() || this.thursday.isNotEmpty() || this.friday.isNotEmpty() || this.saturday.isNotEmpty() || this.sunday.isNotEmpty()
}