package com.app.cheatbite.app.domain

import com.app.cheatbite.app.domain.model.FoodItem
import com.app.cheatbite.app.domain.model.Foods
import com.app.cheatbite.app.domain.model.Suggestion

class NutritionEngine {

    fun calculateTargetNutrition(
        userPlannedMeal: String, 
        dietDatabase: Foods
    ): FoodItem {
        var tCal = 0.0
        var tPro = 0.0
        var tCarb = 0.0
        var tFat = 0.0
        var tSat = 0.0
        var tFib = 0.0

        val normalizedInput = userPlannedMeal.lowercase()

        dietDatabase.foodItems.forEach { item ->
            if (normalizedInput.contains(item.foodItemName.lowercase())) {
                tCal += item.calories
                tPro += item.protein
                tCarb += item.carbs
                tFat += item.fats
                tSat += item.saturatedFat
                tFib += item.fiber
            }
        }

        return FoodItem(
            foodItemName = "Calculated Target",
            calories = tCal,
            protein = tPro,
            carbs = tCarb,
            fats = tFat,
            saturatedFat = tSat,
            fiber = tFib,
            type = "generic"
        )
    }


    fun findCheatMatches(
        targetItem: FoodItem, 
        userPreferenceType: String,
        cheatDatabase: Foods,
        limit: Int
    ): List<Suggestion> {
        val targetScore = targetItem.totalScore

        return cheatDatabase.foodItems
            .filter { it.type.equals(userPreferenceType, ignoreCase = true) }
            .map { cheatMeal ->
                val cheatScore = cheatMeal.totalScore
                val diff = kotlin.math.abs(cheatScore - targetScore)
                val matchPoints = when {
                    diff <= 30 -> 5
                    diff <= 80 -> 4
                    diff <= 150 -> 3
                    else -> 2
                }
                Suggestion(cheatMeal, matchPoints)
            }
            .sortedByDescending { it.matchScore }
            .take(limit)
    }

    private val FoodItem.totalScore: Double
        get() = calories + protein + fiber + saturatedFat + fats + carbs
}