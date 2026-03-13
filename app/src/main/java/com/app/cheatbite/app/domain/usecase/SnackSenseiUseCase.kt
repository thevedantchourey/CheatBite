package com.app.cheatbite.app.domain.usecase

import com.app.cheatbite.app.domain.NutritionEngine
import com.app.cheatbite.app.domain.model.SnackSenseiResponse
import com.app.cheatbite.app.domain.model.UserProfile
import com.app.cheatbite.app.domain.repository.SnackSenseiRepository
import java.time.LocalDateTime
import com.app.cheatbite.core.domain.util.Result

class SnackSenseiUseCase(
    private val repository: SnackSenseiRepository
) {
    private val nutritionEngine = NutritionEngine()

    suspend operator fun invoke(userQuery: String, userProfile: UserProfile): SnackSenseiResponse {
        val conLogic = repository.parseIntent()
        val normalizedInput = userQuery.trim().lowercase()

        val response = conLogic.intents.find { it.patterns.any{ pattern ->
            val regex = "\\b${Regex.escape(pattern)}\\b".toRegex(RegexOption.IGNORE_CASE)
            regex.containsMatchIn(normalizedInput)
        } }

        return when (response?.tag) {
            "suggestion" -> {
                val text = response.responses.random()
                SnackSenseiResponse(message = text)
                handleSuggestionLogic(userQuery, userProfile)
            }
            else -> {
                val text = response?.responses?.random() ?: "I'm not sure."
                SnackSenseiResponse(message = text)
            }
        }
    }

    private suspend fun handleSuggestionLogic(userQuery: String, userProfile: UserProfile): SnackSenseiResponse {

        val dietResult = repository.parseDietCSV()
        val cheatResult = repository.parseCheatMealCSV()

        if (dietResult is Result.Error || cheatResult is Result.Error) {
            return SnackSenseiResponse( message = "I'm having trouble accessing my food database. Please try again later.")
        }

        val dietDb = (dietResult as Result.Success).data
        val cheatDb = (cheatResult as Result.Success).data

        val now = LocalDateTime.now()
        val currentDay = now.dayOfWeek.name.lowercase()

        val todaysMeals = when(currentDay) {
            "monday" -> userProfile.weeklyDiet.monday
            "tuesday" -> userProfile.weeklyDiet.tuesday
            "wednesday" -> userProfile.weeklyDiet.wednesday
            "thursday" -> userProfile.weeklyDiet.thursday
            "friday" -> userProfile.weeklyDiet.friday
            "saturday" -> userProfile.weeklyDiet.saturday
            "sunday" -> userProfile.weeklyDiet.sunday
            else -> emptyList()
        }

        if (todaysMeals.isEmpty()) return SnackSenseiResponse( message = "You don't have a diet plan for $currentDay!")

        val matchedMealEntry = todaysMeals.find { meal ->
            userQuery.contains(meal.time, ignoreCase = true)
        }
        if (matchedMealEntry != null) {
            val plannedFood = matchedMealEntry.food
            val slotName = matchedMealEntry.time

            var targetItem = nutritionEngine.calculateTargetNutrition(plannedFood, dietDb)

            if (targetItem.calories == 0.0) {
                targetItem = targetItem.copy(calories = 500.0)
            }

            val limit = if (userProfile.subscription.equals("Free", true)) 4 else 2

            val suggestions = nutritionEngine.findCheatMatches(
                targetItem = targetItem,
                userPreferenceType = userProfile.mealType,
                cheatDatabase = cheatDb,
                limit = limit
            )

            val sb = StringBuilder()
            sb.append("Swapping [$slotName] ($plannedFood ≈ ${targetItem.calories.toInt()} kcal).\n\n")
            sb.append("Based on your nutritional profile, try these:\n")

            if (suggestions.isEmpty()) {
                sb.append("No matching cheat meals found for your diet type (${userProfile.mealType}).")
            } else {
                suggestions.forEach { suggestion ->
                    sb.append("• ${suggestion.food.foodItemName.replaceFirstChar { it.uppercase() }}")
                    sb.append("(${suggestion.food.calories.toInt()} kcal)\n")
                }
            }

            if (limit == 2) sb.append("\nUpgrade to Pro for more choices!")

            return SnackSenseiResponse(
                message = sb.toString(),
                suggestions = suggestions,
                requiresFeedback = suggestions.isNotEmpty()
            )
        }
        else {
            val availableSlots = todaysMeals.joinToString(", ") { it.time }
            return SnackSenseiResponse(
                message = "For which meal? Today I see: [$availableSlots]...\nFor suggestion ask something like suggest breakfast meal."
            )
        }
    }
}