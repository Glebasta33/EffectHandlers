package com.example.effecthandlers.launched_effect

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LaunchedEffectDemoScreen() {
    var key by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "When LaunchedEffect enters the Composition, it launches a coroutine. \nThe coroutine will be cancelled if LaunchedEffect leaves the composition.",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(0.8f),
            color = MaterialTheme.colors.secondary
        )

        LazyColumn(
            modifier = Modifier.weight(5f)
        ) {
            repeat(7) {
                item { LaunchedEffectedItem(key) }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
        ) {
            Text(
                text = "If LaunchedEffect is recomposed with different keys, the existing coroutine will be cancelled and the new suspend function will be launched in a new coroutine",
                modifier = Modifier
                    .weight(2f)
                    .padding(2.dp),
                color = MaterialTheme.colors.secondary
            )
            Button(
                onClick = { key += 1 },
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp)
            ) {
                Text(
                    text = "Increase key"
                )
            }
        }

    }
}

@Composable
fun LaunchedEffectedItem(
    key: Int
) {
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Current key value: $key")
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
            LaunchedEffect(key1 = key) {
                progress = 0.0f
                repeat(100) {
                    delay(100)
                    progress += 0.01f
                }
            }
        }
    }
}