package com.leo.unipiaudiostories

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.firestore.FirebaseFirestore
import com.leo.unipiaudiostories.utils.DataAgent
import com.leo.unipiaudiostories.utils.StoryModel

@Composable
fun HomeView() {
    val dataBase = DataAgent()
    val storiesList = remember { mutableStateOf<List<StoryModel>>(emptyList()) } // State for the list
    // https://chatgpt.com/c/6794f907-ebe0-800f-a7e0-19671b642999
    // Fetch the data asynchronously
    LaunchedEffect(Unit) {
        val fetchedStories = dataBase.getStoriesList()
        storiesList.value = fetchedStories // Update the state
    }

    // Display the content
    if (storiesList.value.isNotEmpty()) {
        Text(text = storiesList.value[0].title ?: "No Title")
    } else {
        Text(text = "Loading...") // Loading state
    }
}
