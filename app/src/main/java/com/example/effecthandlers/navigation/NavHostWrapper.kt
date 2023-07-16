package com.example.effecthandlers.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
internal fun NavHostWrapper(
    navHostController: NavHostController,
    effectsListScreen: @Composable () -> Unit,
    launchedEffectScreen: @Composable () -> Unit,
    rememberCoroutineScopeScreen: @Composable () -> Unit,
    rememberUpdatedStateScreen : @Composable () -> Unit,
    disposableEffectScreen: @Composable () -> Unit,
    sideEffectScreen: @Composable () -> Unit,
    produceStateScreen: @Composable () -> Unit,
    derivedStateOfScreen: @Composable () -> Unit,
    snapshotFlowScreen: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = EffectHandlerScreen.EffectsListScreen.route,
        modifier = modifier
    ) {
        composable(EffectHandlerScreen.EffectsListScreen.route) {
            effectsListScreen()
        }
        composable(EffectHandlerScreen.LaunchedEffectScreen.route) {
            launchedEffectScreen()
        }
        composable(EffectHandlerScreen.RememberCoroutineScopeScreen.route) {
            rememberCoroutineScopeScreen()
        }
        composable(EffectHandlerScreen.RememberUpdatedStateScreen.route) {
            rememberUpdatedStateScreen()
        }
        composable(EffectHandlerScreen.DisposableEffectScreen.route) {
            disposableEffectScreen()
        }
        composable(EffectHandlerScreen.SideEffectScreen.route) {
            sideEffectScreen()
        }
        composable(EffectHandlerScreen.ProduceStateScreen.route) {
            produceStateScreen()
        }
        composable(EffectHandlerScreen.DerivedStateOfScreen.route) {
            derivedStateOfScreen()
        }
        composable(EffectHandlerScreen.SnapshotFlowScreen.route) {
            snapshotFlowScreen()
        }
    }
}