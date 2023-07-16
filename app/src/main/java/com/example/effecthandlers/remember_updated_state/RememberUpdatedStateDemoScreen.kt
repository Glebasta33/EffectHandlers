package com.example.effecthandlers.remember_updated_state

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun RememberCalculation(operation: () -> Unit) {
    val firstlyRememberedOperation = remember { operation }

    var progress by remember { mutableStateOf(0.0f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    LaunchedEffect(true) {
        repeat(100) {
            delay(50)
            progress += 0.01f
        }
        firstlyRememberedOperation()
    }

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(300.dp)) {
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.rotate(90f)
        )
    }
}

@Composable
fun RememberUpdatedStateCalculation(operation: () -> Unit) {
    val updatedOperation by rememberUpdatedState(newValue = operation)

    var progress by remember { mutableStateOf(0.0f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    LaunchedEffect(true) {
        repeat(100) {
            delay(50)
            progress += 0.01f
        }
        updatedOperation()
    }

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(300.dp)) {
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.rotate(90f)
        )
    }
}

@Composable
fun RememberUpdatedStateDemoScreen() {

    var showCalculation by remember { mutableStateOf(false) }
    var showCalculation2 by remember { mutableStateOf(false) }

    val radioOptions = listOf(
        "Бургер \uD83C\uDF2E",
        "Пицца \uD83C\uDF55",
        "Суши \uD83C\uDF63",
        "Шавуха \uD83C\uDF2F"
    )

    val (selectedOption: String, onOptionsSelected: (String) -> Unit) = remember {
        mutableStateOf(radioOptions[0])
    }

    val (selectedOption2: String, onOptionsSelected2: (String) -> Unit) = remember {
        mutableStateOf(radioOptions[0])
    }

    var firstResult by remember { mutableStateOf("") }
    var secondResult by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "In some situations you might want to capture a value in your effect that, if it changes, you do not want the effect to restart. \n To make sure that the lambda always contains the latest value, lambda needs to be wrapped with the rememberUpdatedState.",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colors.secondary
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(
                    text = "remember:",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .padding(vertical = 10.dp),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
                Column(
                    modifier = Modifier
                        .height(140.dp)
                        .padding(start = 20.dp)
                ) {
                    radioOptions.forEach { text ->
                        Column(
                            modifier = Modifier.selectableGroup()
                        ) {
                            Row(
                                modifier = Modifier
                                    .selectable(
                                        selected = (text == selectedOption),
                                        onClick = {
                                            if (!showCalculation) {
                                                showCalculation = true
                                            }
                                            onOptionsSelected(text)
                                        },
                                        role = Role.RadioButton
                                    )
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                RadioButton(selected = (text == selectedOption), onClick = null)
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = text)
                            }
                        }
                    }
                }
                Column(Modifier.height(300.dp)) {
                    if (showCalculation) {
                        RememberCalculation {
                            showCalculation = false
                            firstResult = selectedOption
                        }
                    }
                }
                Text(
                    text = firstResult,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(
                    text = "rememberUpdatedState:",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(vertical = 10.dp),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
                Column(
                    modifier = Modifier
                        .height(140.dp)
                        .padding(start = 20.dp)
                ) {
                    radioOptions.forEach { text ->
                        Column(
                            modifier = Modifier.selectableGroup()
                        ) {
                            Row(
                                modifier = Modifier
                                    .selectable(
                                        selected = (text == selectedOption2),
                                        onClick = {
                                            if (!showCalculation2) {
                                                showCalculation2 = true
                                            }
                                            onOptionsSelected2(text)
                                        },
                                        role = Role.RadioButton
                                    )
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                RadioButton(
                                    selected = (text == selectedOption2),
                                    onClick = null
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = text)
                            }
                        }
                    }
                }
                Column(Modifier.height(300.dp)) {
                    if (showCalculation2) {
                        RememberUpdatedStateCalculation {
                            showCalculation2 = false
                            secondResult = selectedOption2
                        }
                    }
                }
                Text(
                    text = secondResult,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        }
    }
}