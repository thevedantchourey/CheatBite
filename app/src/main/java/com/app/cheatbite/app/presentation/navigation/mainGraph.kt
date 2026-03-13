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
import com.app.cheatbite.app.presentation.AccountScreen
import com.app.cheatbite.app.presentation.HomeScreen
import com.app.cheatbite.app.viewmodels.AccountViewModel
import com.app.cheatbite.app.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    modifier: Modifier,
    enter: (AnimatedContentTransitionScope<*>.() -> EnterTransition),
    exit: (AnimatedContentTransitionScope<*>.() -> ExitTransition),
    popEnter: (AnimatedContentTransitionScope<*>.() -> EnterTransition),
    popExit: (AnimatedContentTransitionScope<*>.() -> ExitTransition)
) {
    navigation(
        startDestination = Screen.Home.route,
        route = "mainGraph"
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = enter,
            exitTransition = exit,
            popEnterTransition = popEnter,
            popExitTransition = popExit
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("mainGraph")
            }
            val mainViewModel = koinViewModel<MainViewModel>(
                viewModelStoreOwner = parentEntry
            )
            HomeScreen(
                modifier = modifier,
                viewModel = mainViewModel,
                onToAccount = {
                    navController.navigate(Screen.Account.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Screen.Account.route,
            enterTransition = enter,
            exitTransition = exit,
            popEnterTransition = popEnter,
            popExitTransition = popExit
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("mainGraph")
            }
            val accountViewModel = koinViewModel<AccountViewModel>(
                viewModelStoreOwner = parentEntry
            )

            AccountScreen(
                modifier = modifier,
                viewModel = accountViewModel,
                onToPrevious = { navController.popBackStack() },
                onSignOutSuccess = {
                    navController.navigate(Screen.SignUp.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}