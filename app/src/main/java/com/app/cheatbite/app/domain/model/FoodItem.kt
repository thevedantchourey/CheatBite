package com.app.cheatbite.app.domain.model

data class FoodItem(
    val foodItemName: String = "",
    val calories: Double = 0.0,
    val protein: Double = 0.0,
    val carbs: Double = 0.0,
    val fats: Double = 0.0,
    val saturatedFat: Double = 0.0,
    val fiber: Double = 0.0,
    val type: String = "veg"
)


