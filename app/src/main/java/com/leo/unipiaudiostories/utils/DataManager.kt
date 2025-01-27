package com.leo.unipiaudiostories.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class DataManager {
    private val dataBase = FirebaseFirestore.getInstance()
    private val storyCollection = dataBase.collection(AppConstants.STORY_COLLECTION)
    private val storiesList = mutableListOf<StoryModel>()

    suspend fun getStoriesList(): List<StoryModel> {
        try {
            // Use `await()` to get the Firestore data synchronously within the coroutine
            val querySnapshot: QuerySnapshot = storyCollection.get().await()

            for (document in querySnapshot.documents) {
                val story = document
                    .toObject(StoryModel::class.java)
                    ?.also{ it.id = document.id }

                if (story != null) {
                    storiesList.add(story)
                }
            }
        } catch (exception: Exception) {
            Log.e("StoryRepository", "Error fetching stories", exception)
        }

        return storiesList
    }

    private fun getStoriesIdAndTitleMap(): Map<String, String?> {
        val storiesIdAndTitleMap = mutableMapOf<String, String?>()

        for (model in storiesList) {
            if (model.id.isBlank()) continue
            storiesIdAndTitleMap[model.id] = model.title
        }

        return storiesIdAndTitleMap
    }

    fun getStoriesTitleAndStats(
        idAndStatsMap: Map<String, Int>
    ): Map<String, Int> {
        val titleAndStatsMap = mutableMapOf<String, Int>()

        idAndStatsMap.forEach{ entry ->
            val title = getStoriesIdAndTitleMap()[entry.key] ?: ""
            titleAndStatsMap[title] = entry.value
        }

        return titleAndStatsMap
    }
}