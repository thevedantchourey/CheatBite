package com.app.cheatbite.core.data.local.dto

import kotlinx.serialization.Serializable

@Serializable
data class FoodItemDto(
    val foodItemName: String = "",
    val calories: Double = 0.0,
    val protein: Double = 0.0,
    val carbs: Double = 0.0,
    val fats: Double = 0.0,
    val saturatedFat: Double = 0.0,
    val fiber: Double = 0.0,
    val type: String = "veg"
)