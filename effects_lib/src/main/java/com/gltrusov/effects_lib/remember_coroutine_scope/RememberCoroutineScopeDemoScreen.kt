package com.gltrusov.effects_lib.remember_coroutine_scope

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gltrusov.effects_lib.utils.HyperlinkText
import com.gltrusov.effects_lib.utils.createLinkToFileFunction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun RememberCoroutineScopeDemoScreen() {

    val newLink = createLinkToFileFunction(
        context = LocalContext.current,
        pack = object {}::class.java.`package`,
        method = object {}::class.java.enclosingMethod
    ).replace("app", "effects_lib")

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "rememberCoroutineScope returns CoroutineScope bound to the point of the Composition where it's called. \nThis scope can be launched from not composable context (e.g. onClick callback). \nThe scope will be cancelled when the call leaves the Composition .",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(1f),
            color = MaterialTheme.colors.secondary
        )

        LazyColumn(
            modifier = Modifier.weight(5f)
        ) {
            repeat(7) {
                item { LaunchedEffectedItem() }
            }
        }

        HyperlinkText(
            linkText = newLink,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 4.dp, bottom = 2.dp)
        )
    }
}

@Composable
private fun LaunchedEffectedItem() {
    val scope = rememberCoroutineScope()
    var progress by remember { mutableStateOf(0.0f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(8.dp)
                .padding(top = 80.dp)
        ) {
            LinearProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                scope.launch {
                    progress = 0.0f
                    repeat(100) {
                        delay(100)
                        progress += 0.01f
                    }
                }
            }) {
                Text(
                    text = "Launch"
                )
            }
        }
    }
}