package com.leo.unipiaudiostories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.leo.unipiaudiostories.utils.AppConstants
import com.leo.unipiaudiostories.utils.StatsManager
import com.leo.unipiaudiostories.utils.StoryModel
import java.util.Locale

@Composable
fun DetailsView(
    stats: StatsManager,
    homeState: MutableState<String>,
    updateAppLocale: (Locale) -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.secondary)
            .statusBarsPadding()
    ) {
        Header(homeState)
        StatsItemView(stats)
        Settings(updateAppLocale)
    }
}

@Composable
private fun Header(homeState: MutableState<String>) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = 8.dp, end = 8.dp, top = 24.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        Box (
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable {
                    homeState.value = AppConstants.STATE_HOME
                }
        ) {
            Icon(
                modifier = Modifier
                    //.padding(end = 8.dp, bottom = 4.dp)
                    .size(48.dp)
                    .graphicsLayer {
                        rotationZ = -90f // Rotate the arrow 90 degrees counterclockwise
                    },
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,

                )
        }
        Text(
            textAlign = TextAlign.Start,
            text = stringResource(R.string.stats_header_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun Settings(updateAppLocale: (Locale) -> Unit) {
    Column (
        modifier = Modifier.padding(24.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        LanguageDropdownButton(updateAppLocale)
    }
}

@Composable
private fun LanguageDropdownButton(updateAppLocale: (Locale) -> Unit) {
    val context = LocalContext.current
    val expanded = remember { mutableStateOf(false) }
    val options = listOf(
        stringResource(R.string.english_label),
        stringResource(R.string.greek_label),
        stringResource(R.string.french_label))
    val selectedOption = remember { mutableStateOf(
        options[localeToIndex(getAppLanguage(context))]) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = stringResource(id = R.string.language_label),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall,
        )

        Column {
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(text = selectedOption.value)
            }

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                containerColor = MaterialTheme.colorScheme.secondary,
            ) {
                options.forEachIndexed { i, language ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption.value = language
                            expanded.value = false
                            updateAppLocale.invoke(indexToLocale(i))
                        },
                        text = { Text(language) },
                        colors = MenuDefaults.itemColors(
                            textColor = MaterialTheme.colorScheme.primary,
                        ),
                    )
                }
            }
        }
    }
}

private fun indexToLocale(index: Int): Locale {
    return when(index) {
        0 -> Locale(AppConstants.ENGLISH)
        1 -> Locale(AppConstants.GREEK)
        else -> Locale(AppConstants.FRENCH)
    }
}

private fun localeToIndex(locale: String): Int {
    return when(locale) {
        AppConstants.ENGLISH -> 0
        AppConstants.GREEK -> 1
        else -> 2
    }
}