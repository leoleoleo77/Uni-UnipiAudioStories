package com.leo.unipiaudiostories

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.leo.unipiaudiostories.ui.theme.FunTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent { MyApp(this) }
    }
}

@Composable
fun MyApp(activity: Activity) {
    val context = LocalContext.current

    val updateAppLocale: (Locale) -> Unit = { newLocale ->
        val config = context.resources.configuration
        Locale.setDefault(newLocale)
        config.setLocale(newLocale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        updateLanguage(context, newLocale.toLanguageTag())

        val intent = activity.intent
        activity.finish()
        activity.startActivity(intent)
    }

    val config = context.resources.configuration
    Locale.setDefault(Locale(getAppLanguage(context)))
    config.setLocale(Locale(getAppLanguage(context)))
    context.createConfigurationContext(config)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)


    FunTheme { HomeView(updateAppLocale) }
}