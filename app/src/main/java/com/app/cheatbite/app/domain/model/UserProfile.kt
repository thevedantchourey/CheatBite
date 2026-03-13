package com.app.cheatbite.app.domain.model

data class UserProfile(
    val userId: String = "",
    val avatar: String = "",
    val username: String = "",
    val email: String = "",
    val subscription: String = "Free",
    val weeklyDiet: WeekDiet = WeekDiet(),
    val mealType: String = "veg",
    val history: List<Suggestion> = emptyList()
)


