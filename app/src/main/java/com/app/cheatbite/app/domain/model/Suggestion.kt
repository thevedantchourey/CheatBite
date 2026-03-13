package com.app.cheatbite.app.domain.model

data class Suggestion(
    val food: FoodItem = FoodItem(),
    val matchScore: Int = 0
)