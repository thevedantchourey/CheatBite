package com.app.cheatbite.app.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String
) {
    val enter: (AnimatedContentTransitionScope<*>.() -> EnterTransition) = {
        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300))
    }
    val exit: (AnimatedContentTransitionScope<*>.() -> ExitTransition) = {
        slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
    }
    val popEnter: (AnimatedContentTransitionScope<*>.() -> EnterTransition) = {
        slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300))
    }
    val popExit: (AnimatedContentTransitionScope<*>.() -> ExitTransition) = {
        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
    }


    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authGraph(
            navController = navController,
            modifier = modifier,
            enter = enter,
            exit = exit,
            popEnter = popEnter,
            popExit = popExit
        )
        mainGraph(
            navController = navController,
            modifier = modifier,
            enter = enter,
            exit = exit,
            popEnter = popEnter,
            popExit = popExit
        )
    }
}
