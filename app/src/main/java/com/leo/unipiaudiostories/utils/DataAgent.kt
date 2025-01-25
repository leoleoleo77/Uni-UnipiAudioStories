package com.leo.unipiaudiostories.utils

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class DataAgent {
    private val dataBase = FirebaseFirestore.getInstance()
    private val storyCollection = dataBase.collection(AppConstants.STORY_COLLECTION)

    suspend fun getStoriesList(): List<StoryModel> {
        val storiesList = mutableListOf<StoryModel>()

        try {
            // Use `await()` to get the Firestore data synchronously within the coroutine
            val querySnapshot: QuerySnapshot = storyCollection.get().await()

            for (document in querySnapshot.documents) {
                val story = document.toObject(StoryModel::class.java)

                if (story != null) {
                    storiesList.add(story)
                }
            }
        } catch (exception: Exception) {
            Log.e("StoryRepository", "Error fetching stories", exception)
        }

        return storiesList
    }
}