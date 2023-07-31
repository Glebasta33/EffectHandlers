package com.gltrusov.effects_lib.utils

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

//TODO: Удалить после создания utils-библиотеки
@Composable
fun HyperlinkText(
    fullText: String = "GitHub",
    linkText: String,
    modifier: Modifier = Modifier,
    linkTextColor: Color = Color.Blue,
    fontSize: TextUnit = TextUnit.Unspecified,
    linkTextFontWeight: FontWeight = FontWeight.Medium,
    linkTextDecoration: TextDecoration = TextDecoration.Underline
) {
    val fullTextAnnotated = buildAnnotatedString {
        append(fullText)
        addStyle(
            style = SpanStyle(
                color = linkTextColor,
                fontSize = fontSize,
                fontWeight = linkTextFontWeight,
                textDecoration = linkTextDecoration
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = fullTextAnnotated,
        onClick = {
            uriHandler.openUri(linkText)
        }
    )
}