package com.app.cheatbite.app.presentation.navigation

sealed class Screen(
    val route: String
) {
    data object Home : Screen("home")
    data object Account : Screen("account")
    data object SignUp : Screen("signUp")
    data object Preferences : Screen("preferences")


}