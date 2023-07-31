package com.gltrusov.effects_lib.derived_state_of

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gltrusov.effects_lib.utils.HyperlinkText
import com.gltrusov.effects_lib.utils.createLinkToFileFunction

@Composable
internal fun DerivedStateOfDemoScreen() {

    val newLink = createLinkToFileFunction(
        context = LocalContext.current,
        pack = object {}::class.java.`package`,
        method = object {}::class.java.enclosingMethod
    ).replace("app", "effects_lib")

    Box {
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
        HyperlinkText(
            linkText = newLink,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 4.dp, bottom = 2.dp)
        )
    }
}

@Composable
fun CountDisplay(count: State<Int>) {
    Text("a + b = ${count.value}", fontSize = 36.sp, modifier = Modifier.padding(50.dp))
}
//Тестовый коммит с телефона
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
