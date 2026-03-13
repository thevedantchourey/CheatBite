package com.app.cheatbite.app.data.mapper

import com.app.cheatbite.app.domain.model.FoodItem
import com.app.cheatbite.core.data.local.dto.FoodItemDto

fun FoodItemDto.toFoodItem(): FoodItem {
    return FoodItem(
        foodItemName = foodItemName,
        calories = calories,
        protein = protein,
        carbs = carbs,
        fats = fats,
        saturatedFat = saturatedFat,
        fiber = fiber,
        type = type
    )
}

fun FoodItem.toFoodItemDto(): FoodItemDto {
    return FoodItemDto(
        foodItemName = foodItemName,
        calories = calories,
        protein = protein,
        carbs = carbs,
        fats = fats,
        saturatedFat = saturatedFat,
        fiber = fiber,
        type = type
    )
}