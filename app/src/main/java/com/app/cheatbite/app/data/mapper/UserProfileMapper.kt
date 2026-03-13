package com.app.cheatbite.app.data.mapper

import com.app.cheatbite.app.domain.model.UserProfile
import com.app.cheatbite.core.data.remote.dto.UserProfileDto


fun UserProfileDto.toUserProfile(): UserProfile {
    return UserProfile(
         userId = userId!!,
         avatar = avatar!!,
         username = username!!,
         email = email!!,
         subscription = subscription!!,
         weeklyDiet = weeklyDiet!!.toWeekDiet(),
         mealType = mealType!!,
         history = history.map { it.toSuggestion() }
    )
}

fun UserProfile.toUserProfileDto(): UserProfileDto {
    return UserProfileDto(
        userId = userId,
        avatar = avatar,
        username = username,
        email = email,
        subscription = subscription,
        weeklyDiet = weeklyDiet.toWeekDietDto(),
        mealType = mealType,
        history = history.map { it.toSuggestionDto() }
    )
}