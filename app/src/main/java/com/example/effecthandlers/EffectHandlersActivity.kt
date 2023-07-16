package com.example.effecthandlers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.effecthandlers.derived_state_of.DerivedStateOfDemoScreen
import com.example.effecthandlers.disposable_effect.DisposableEffectDemoScreen
import com.example.effecthandlers.launched_effect.LaunchedEffectDemoScreen
import com.example.effecthandlers.navigation.EffectHandlerScreen
import com.example.effecthandlers.navigation.NavHostWrapper
import com.example.effecthandlers.navigation.NavigationState
import com.example.effecthandlers.navigation.rememberNavigationState
import com.example.effecthandlers.produce_state.ProduceStateDemoScreen
import com.example.effecthandlers.remember_coroutine_scope.RememberCoroutineScopeDemoScreen
import com.example.effecthandlers.remember_updated_state.RememberUpdatedStateDemoScreen
import com.example.effecthandlers.side_effect.SideEffectDemoScreen
import com.example.effecthandlers.snapshot_flow.SnapshotFlowDemoScreen
import com.example.effecthandlers.ui.theme.EffectHandlersTheme

class EffectHandlersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigationState = rememberNavigationState()
            val screenTitle = remember { mutableStateOf(EFFECT_HANDLERS_TITLE) }

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
            EffectHandlersTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = screenTitle.value)
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    screenTitle.value = EFFECT_HANDLERS_TITLE
                                    if (navigationState.navHostController.currentBackStackEntry?.destination?.route == EffectHandlerScreen.EFFECTS_LIST) {
                                        onBackPressed()
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
                            screenTitle.value = EFFECT_HANDLERS_TITLE
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
        }
    }

    companion object {
        private const val EFFECT_HANDLERS_TITLE = "Effect Handlers"

        fun openScreen(context: Context) = context.startActivity(Intent(context, EffectHandlersActivity::class.java))
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