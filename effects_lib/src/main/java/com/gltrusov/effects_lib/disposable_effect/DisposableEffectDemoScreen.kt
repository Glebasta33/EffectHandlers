package com.gltrusov.effects_lib.disposable_effect

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.gltrusov.effects_lib.utils.HyperlinkText
import com.gltrusov.effects_lib.utils.createLinkToFileFunction

@Composable
internal fun DisposableEffectDemoScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    var eventName by remember { mutableStateOf("") }

    val newLink = createLinkToFileFunction(
        context = LocalContext.current,
        pack = object {}::class.java.`package`,
        method = object {}::class.java.enclosingMethod
    ).replace("app", "effects_lib")


    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "For side effects that need to be cleaned up after the keys change or if the composable leaves the Composition, use DisposableEffect.",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colors.secondary
        )
        Text(text = eventName, fontSize = 32.sp)
        Text(
            text = "In this example, the effect will add the observer to the lifecycleOwner. It observes lifecycle events. \nIf lifecycleOwner changes, the effect is disposed and restarted with the new lifecycleOwner.",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colors.secondary
        )
        HyperlinkText(
            linkText = newLink,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 4.dp, bottom = 2.dp)
        )
    }


    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            eventName = event.name
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

}