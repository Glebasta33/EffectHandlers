package com.example.effecthandlers.navigation

internal sealed class EffectHandlerScreen(
    val route: String
) {

    object EffectsListScreen : EffectHandlerScreen(EFFECTS_LIST)
    object LaunchedEffectScreen : EffectHandlerScreen(LAUNCHED_EFFECT)
    object RememberCoroutineScopeScreen : EffectHandlerScreen(REMEMBER_COROUTINE_SCOPE)
    object RememberUpdatedStateScreen : EffectHandlerScreen(REMEMBER_UPDATE_STATE)
    object DisposableEffectScreen : EffectHandlerScreen(DISPOSABLE_EFFECT)
    object SideEffectScreen : EffectHandlerScreen(SIDE_EFFECT)
    object ProduceStateScreen : EffectHandlerScreen(PRODUCE_STATE)
    object DerivedStateOfScreen : EffectHandlerScreen(DERIVED_STATE_OF)
    object SnapshotFlowScreen : EffectHandlerScreen(SNAPSHOT_FLOW)

    companion object {
        const val EFFECTS_LIST = "effects_list"
        const val LAUNCHED_EFFECT = "LaunchedEffect"
        const val REMEMBER_COROUTINE_SCOPE = "rememberCoroutineScope"
        const val REMEMBER_UPDATE_STATE = "rememberUpdatedState"
        const val DISPOSABLE_EFFECT = "DisposableEffect"
        const val SIDE_EFFECT = "SideEffect"
        const val PRODUCE_STATE = "produceState"
        const val DERIVED_STATE_OF = "derivedStateOf"
        const val SNAPSHOT_FLOW = "snapshotFlow"
    }
}
