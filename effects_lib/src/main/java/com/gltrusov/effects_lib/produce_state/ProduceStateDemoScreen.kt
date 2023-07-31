package com.gltrusov.effects_lib.produce_state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gltrusov.effects_lib.R
import com.gltrusov.effects_lib.utils.HyperlinkText
import com.gltrusov.effects_lib.utils.createLinkToFileFunction
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun ProduceStateDemoScreen() {

    var isLinkValid by remember { mutableStateOf(false) }

    val newLink = createLinkToFileFunction(
        context = LocalContext.current,
        pack = object {}::class.java.`package`,
        method = object {}::class.java.enclosingMethod
    ).replace("app", "effects_lib")

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Text(
            text = "Use produceState to convert non-Compose state into Compose state as composable function, which returns State that can be used in other composables. \nproduceState is launched when it enters the Composition, and will be cancelled when it leaves the Composition",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colors.secondary
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            val image = loadNetworkImage(isLinkValid = isLinkValid).value
            when (image) {
                Result.Loading -> {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "State: Result.Loading")
                }
                Result.Error -> {
                    Text(text = "State: Result.Error", color = Color.Red)
                }
                is Result.Success -> {
                    Image(
                        painter = painterResource(id = R.drawable.cat),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = image.getValue())
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { isLinkValid = false }) {
                Text(text = "Load with INVALID link")
            }
            Button(onClick = { isLinkValid = true }) {
                Text(text = "Load with VALID link")
            }

            Spacer(modifier = Modifier.height(200.dp))

            HyperlinkText(
                linkText = newLink,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 4.dp, bottom = 2.dp)
            )
        }
    }

}

@Composable
fun loadNetworkImage(
    isLinkValid: Boolean
): State<Result<String>> {

    val scope = rememberCoroutineScope()

    return produceState<Result<String>>(initialValue = Result.Loading, key1 = isLinkValid) {

        scope.launch {
            val image = "State: Result.Success"

            value = Result.Loading

            delay(1500)

            value = if (isLinkValid) {
                Result.Success(image)
            } else {
                Result.Error
            }

            awaitDispose {
                scope.cancel()
            }
        }
    }
}

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    object Error : Result<Nothing>()
    class Success<T>(private val t: T?) : Result<T>() {
        fun getValue(): T = t!!
    }
}