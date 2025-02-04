package com.leo.unipiaudiostories

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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

    // callback method to pass into the language selector.
    // now that I think about it, it would have been smarter to just
    // pass the activity...
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

    // get the saved locale from sharedPreferences and set it
    val config = context.resources.configuration
    Locale.setDefault(Locale(getAppLanguage(context)))
    config.setLocale(Locale(getAppLanguage(context)))
    context.createConfigurationContext(config)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)


    FunTheme { HomeView(updateAppLocale) }
}