package com.example.effecthandlers.derived_state_of

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun DerivedStateOfDemoScreen() {
    Column {
        Text(
            text = "derivedStateOf: convert one or multiple state objects into another state. \nUsing this function guarantees that the calculation will only occur whenever one of the states used in the derivedStateOf{} changes.",
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
fun CountDisplay(count: State<Int>) {
    Text("a + b = ${count.value}", fontSize = 36.sp, modifier = Modifier.padding(50.dp))
}

@Composable
fun Example() {
    var a by remember { mutableStateOf(0) }
    var b by remember { mutableStateOf(0) }
    val sum = remember { derivedStateOf { a + b } }
    // Changing either a or b will cause CountDisplay to recompose but not trigger Example
    // to recompose.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        CountDisplay(sum)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { a += 1 }) {
                Text(text = "a++")
            }
            Button(onClick = { b += 1 }) {
                Text(text = "b++")
            }
        }
    }

}