package com.leo.unipiaudiostories

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.leo.unipiaudiostories.utils.AppConstants
import com.leo.unipiaudiostories.utils.DataAgent
import com.leo.unipiaudiostories.utils.StoryModel

@Composable
fun HomeView() {
    val dataBase = DataAgent()
    val storiesList = remember { mutableStateOf<List<StoryModel>>(emptyList()) }
    val homeState = remember { mutableStateOf(AppConstants.STATE_HOME) }
    val selectedStory = remember { mutableStateOf<StoryModel?>(null) }

    LaunchedEffect(Unit) {
        val fetchedStories = dataBase.getStoriesList()
        storiesList.value = fetchedStories // Update the state
    }

    if (homeState.value == AppConstants.STATE_HOME) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.secondary)
                .statusBarsPadding()
        ) {
            Header()
            storiesList.value.forEach { story ->
                Story(
                    storyModel = story,
                    selectedStory = selectedStory,
                    homeState= homeState)
            }
        }
    } else if (homeState.value == AppConstants.STATE_STORY) {
        selectedStory.value?.let {
            StoryView(it, homeState)
        }
    }
}

@Composable
private fun Header(
    //homeState: MutableState<String>,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(8.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            textAlign = TextAlign.Start,
            text = stringResource(R.string.header_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        Box (
            modifier = Modifier
                //.align(Alignment.Bottom)
//                .clickable {
//                    homeState.value = AppConstants.NAVIGATION_PROFILE_DETAILS
//                }
        ) {
            Image(
                modifier = Modifier
                    //.padding(end = 8.dp, bottom = 4.dp)
                    .size(48.dp),
                painter = painterResource(R.drawable.img_stats),
                contentDescription = null,
//                tint = MaterialTheme.colorScheme.primary,

                )
        }
    }
}

@Composable
private fun Story(
    storyModel: StoryModel,
    selectedStory: MutableState<StoryModel?>,
    homeState: MutableState<String>,
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                homeState.value = AppConstants.STATE_STORY
                selectedStory.value = storyModel
            }
    ) {
        // Background image
        AsyncImage(
            model = storyModel.image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth, // Fit the image to the width
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f))

        // Black gradient box with title and subtitle
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(68.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, // Fade to transparent
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 0.8f),
                            Color.Black// Start with black
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp), // Padding for the text inside the box
                verticalArrangement = Arrangement.Bottom
            ) {
                // Title
                Text(
                    text = storyModel.title ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                val storyInfo = "${storyModel.author} • ${storyModel.year}"
                Text(
                    text = storyInfo,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

