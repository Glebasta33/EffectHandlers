package com.example.effecthandlers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.effecthandlers.ui.theme.EffectHandlersTheme

class EffectHandlersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EffectHandlersTheme {
                EffectHandlersScreen(activity = this)
            }
        }
    }

    companion object {

        fun openScreen(context: Context) =
            context.startActivity(Intent(context, EffectHandlersActivity::class.java))
    }
}