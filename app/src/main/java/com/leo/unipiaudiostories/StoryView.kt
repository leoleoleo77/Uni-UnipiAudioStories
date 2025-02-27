package com.leo.unipiaudiostories

import android.speech.tts.TextToSpeech
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.leo.unipiaudiostories.utils.AppConstants
import com.leo.unipiaudiostories.utils.StatsManager
import com.leo.unipiaudiostories.utils.StoryModel
import java.util.Locale

@Composable
fun StoryView(
    story: StoryModel,
    stats: StatsManager,
    homeState: MutableState<String>
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.secondary)
            .statusBarsPadding()
    ) {
        Header(story, homeState)
        AsyncImage(
            model = story.image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth())
        StoryContainer(story.story ?: "")
    }
    FloatingPlayButton(story, stats)
}
@Composable
private fun Header(
    story: StoryModel,
    homeState: MutableState<String>,
) {
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
            text = story.title ?: "",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun StoryContainer(content: String) {
    Text(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 92.dp),
        text = content,
        style = MaterialTheme.typography.displayLarge
    )
}

@Composable
fun FloatingPlayButton(story: StoryModel, stats: StatsManager) {
    val context = LocalContext.current
    var tts: TextToSpeech? = remember { null } // Remember the TTS instance
    val isSpeaking = remember {
        mutableStateOf(false)
    }

    // Initialize TextToSpeech
    LaunchedEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault() // Set language to device's default
            }
        }
    }

    // Cleanup TextToSpeech when the Composable is removed
    DisposableEffect(Unit) {
        onDispose {
            tts?.shutdown() // Release TTS resources
        }
    }

    // UI with FloatingActionButton
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = {
                if (isSpeaking.value) {
                    tts?.stop()
                } else {
                    stats.incrementReadCount(story.id)
                    tts?.speak(story.story, TextToSpeech.QUEUE_FLUSH, null, null)
                }
                isSpeaking.value = !isSpeaking.value
            },
            modifier = Modifier.align(Alignment.BottomEnd),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = when (isSpeaking.value) {
                    true -> Icons.Default.Mic
                    false -> Icons.Default.MicOff
                },
                contentDescription = "Speak",
                tint = Color.White
            )
        }
    }
}
