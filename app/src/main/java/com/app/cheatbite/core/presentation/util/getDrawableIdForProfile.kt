package com.app.cheatbite.core.presentation.util

import com.app.cheatbite.R

fun getDrawableIdForProfile(userProfile: String?): Int {
    return when (userProfile) {
        "male_profile01" -> R.drawable.male_profile01
        "male_profile02" -> R.drawable.male_profile02
        "male_profile03" -> R.drawable.male_profile03
        "female_profile01" -> R.drawable.female_profile01
        "female_profile02" -> R.drawable.female_profile02
        "female_profile03" -> R.drawable.female_profile03
        else -> {R.drawable.base_profile}
    }
}