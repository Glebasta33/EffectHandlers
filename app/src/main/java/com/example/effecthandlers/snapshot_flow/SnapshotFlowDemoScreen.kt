package com.example.effecthandlers.snapshot_flow

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SnapshotFlowDemoScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "snapshotFlow: convert Compose's State into Flows. \nWhen one of the State objects read inside the snapshotFlow block mutates, the Flow will emit the new value to its collector.",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.height(100.dp))
        Example()

    }
}

@Composable
fun Example() {
    var a by remember { mutableStateOf(0) }
    var b by remember { mutableStateOf(0) }
    var sum by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val sumFlow = snapshotFlow { a + b }
    var isProgress by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        LaunchedEffect(true) {
        }


        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isProgress) {
                CircularProgressIndicator()
            }
            Text("a + b = ${sum}", fontSize = 36.sp, modifier = Modifier.padding(50.dp))
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                a += 1
                scope.launch {
                    sumFlow.collect {
                        isProgress = true
                        delay(1000)
                        isProgress = false
                        sum = it
                    }
                }
            }) {
                Text(text = "a++")
            }
            Button(onClick = {
                b += 1
                scope.launch {
                    sumFlow.collect {
                        isProgress = true
                        delay(1000)
                        isProgress = false
                        sum = it
                    }
                }
            }) {
                Text(text = "b++")
            }
        }
    }

}