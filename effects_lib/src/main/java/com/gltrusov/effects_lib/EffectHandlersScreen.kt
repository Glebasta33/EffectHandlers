package com.example.effecthandlers

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gltrusov.effects_lib.derived_state_of.DerivedStateOfDemoScreen
import com.gltrusov.effects_lib.disposable_effect.DisposableEffectDemoScreen
import com.gltrusov.effects_lib.launched_effect.LaunchedEffectDemoScreen
import com.gltrusov.effects_lib.navigation.EffectHandlerScreen
import com.gltrusov.effects_lib.navigation.NavHostWrapper
import com.gltrusov.effects_lib.navigation.NavigationState
import com.gltrusov.effects_lib.navigation.rememberNavigationState
import com.gltrusov.effects_lib.produce_state.ProduceStateDemoScreen
import com.gltrusov.effects_lib.remember_coroutine_scope.RememberCoroutineScopeDemoScreen
import com.gltrusov.effects_lib.remember_updated_state.RememberUpdatedStateDemoScreen
import com.gltrusov.effects_lib.side_effect.SideEffectDemoScreen
import com.gltrusov.effects_lib.snapshot_flow.SnapshotFlowDemoScreen

@Composable
fun EffectHandlersScreen(activity: Activity) {
    val navigationState = rememberNavigationState()
    val screenTitle = remember { mutableStateOf(EffectHandlersTitle) }

    val screens = listOf(
        EffectHandlerScreen.LaunchedEffectScreen,
        EffectHandlerScreen.RememberCoroutineScopeScreen,
        EffectHandlerScreen.RememberUpdatedStateScreen,
        EffectHandlerScreen.DisposableEffectScreen,
        EffectHandlerScreen.SideEffectScreen,
        EffectHandlerScreen.ProduceStateScreen,
        EffectHandlerScreen.DerivedStateOfScreen,
        EffectHandlerScreen.SnapshotFlowScreen
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = screenTitle.value)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        screenTitle.value = EffectHandlersTitle
                        if (navigationState.navHostController.currentBackStackEntry?.destination?.route == EffectHandlerScreen.EFFECTS_LIST) {
                            activity.onBackPressed()
                        } else {
                            navigationState.navHostController.popBackStack()
                        }

                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { contentPadding ->
        NavHostWrapper(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            navHostController = navigationState.navHostController,
            effectsListScreen = {
                EffectsList(screens, navigationState) { route ->
                    screenTitle.value = route
                }
                screenTitle.value = EffectHandlersTitle
            },
            launchedEffectScreen = { LaunchedEffectDemoScreen() },
            rememberCoroutineScopeScreen = { RememberCoroutineScopeDemoScreen() },
            rememberUpdatedStateScreen = { RememberUpdatedStateDemoScreen() },
            disposableEffectScreen = { DisposableEffectDemoScreen() },
            sideEffectScreen = { SideEffectDemoScreen() },
            produceStateScreen = { ProduceStateDemoScreen() },
            derivedStateOfScreen = { DerivedStateOfDemoScreen() },
            snapshotFlowScreen = { SnapshotFlowDemoScreen() }
        )
    }
}

@Composable
internal fun EffectsList(
    screens: List<EffectHandlerScreen>,
    navigationState: NavigationState,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(screens) { screen ->
            EffectListItem(name = screen.route) {
                navigationState.navigateTo(screen.route)
                onClick.invoke(screen.route)
            }
        }
    }
}

@Composable
private fun EffectListItem(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick.invoke() }
    ) {
        Text(text = name, modifier = Modifier.padding(8.dp))
    }
}

private const val EffectHandlersTitle = "Effect Handlers"