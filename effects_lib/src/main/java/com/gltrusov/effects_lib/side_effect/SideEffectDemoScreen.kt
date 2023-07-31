package com.gltrusov.effects_lib.side_effect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gltrusov.effects_lib.utils.HyperlinkText
import com.gltrusov.effects_lib.utils.createLinkToFileFunction

@Composable
internal fun SideEffectDemoScreen() {

    val newLink = createLinkToFileFunction(
        context = LocalContext.current,
        pack = object {}::class.java.`package`,
        method = object {}::class.java.enclosingMethod
    ).replace("app", "effects_lib")

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "To share Compose state with objects not managed by compose, use the SideEffect composable, as it's invoked on every successful recomposition.",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(1f),
            color = MaterialTheme.colors.secondary
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        LazyColumn(
            modifier = Modifier.weight(9f)
        ) {
            repeat(7) { index ->
                item {
                    LaunchedEffectedItem(index) { loggedText ->
                        text = loggedText
                    }
                }
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
private fun LaunchedEffectedItem(
    itemIndex: Int,
    loggingText: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(8.dp)
                .padding(top = 80.dp)
        ) {
            Text(
                text = "Item $itemIndex",
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            SideEffect {
                loggingText.invoke("Log: Item $itemIndex in composition")
            }
        }
    }
}