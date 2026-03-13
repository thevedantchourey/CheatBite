package com.app.cheatbite.app.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.cheatbite.app.presentation.PreferencesScreen
import com.app.cheatbite.app.presentation.GateWayScreen
import com.app.cheatbite.app.viewmodels.AccountViewModel
import com.app.cheatbite.app.viewmodels.AuthViewModel
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    modifier: Modifier,
    enter: (AnimatedContentTransitionScope<*>.() -> EnterTransition),
    exit: (AnimatedContentTransitionScope<*>.() -> ExitTransition),
    popEnter: (AnimatedContentTransitionScope<*>.() -> EnterTransition),
    popExit: (AnimatedContentTransitionScope<*>.() -> ExitTransition)
) {
    navigation(
        startDestination = Screen.SignUp.route,
        route = "authGraph"
    ) {
        composable(
            route = Screen.SignUp.route,
            enterTransition = enter,
            exitTransition = exit,
            popEnterTransition = popEnter,
            popExitTransition = popExit
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("authGraph")
            }
            val authViewmodel = koinViewModel<AuthViewModel>(
                viewModelStoreOwner = parentEntry
            )

            GateWayScreen(
                modifier = modifier,
                viewModel = authViewmodel,
                onToPreferenceScreen = {
                    navController.navigate(Screen.Preferences.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onToHomeScreen = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Screen.Preferences.route,
            enterTransition = enter,
            exitTransition = exit,
            popEnterTransition = popEnter,
            popExitTransition = popExit
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("authGraph")
            }
            val accountViewModel = koinViewModel<AccountViewModel>(
                viewModelStoreOwner = parentEntry
            )
            PreferencesScreen(
                modifier = modifier,
                viewModel = accountViewModel,
                onToHomeScreen = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Preferences.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}