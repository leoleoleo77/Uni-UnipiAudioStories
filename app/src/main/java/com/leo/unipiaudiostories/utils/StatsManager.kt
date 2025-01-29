package com.leo.unipiaudiostories.utils

import android.content.Context
import android.content.SharedPreferences

class StatsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.USER_STATS, Context.MODE_PRIVATE)

    companion object {
        private val idToTitle = mutableMapOf<String, String>()
    }

    // Function to initialize statistics for new book IDs
    fun initializeStoriesStats(storiesList: List<StoryModel>) {
        val editor = sharedPreferences.edit()

        for (story in storiesList) {
            // Check if the book ID already exists
            if (!sharedPreferences.contains(story.id)) {
                // If it's a new book ID, initialize it with 0 reads
                editor.putInt(story.id, 0)
            }
        }
        idToTitle.putAll(storiesList
            .associateBy({ it.id }, { it.title ?: it.id }))


        // Commit the changes
        editor.apply()
    }

    // Function to increment the read count for a book
    fun incrementReadCount(bookId: String) {
        val currentCount = sharedPreferences.getInt(bookId, 0)
        sharedPreferences.edit().putInt(bookId, currentCount + 1).apply()
    }

//    // Function to retrieve the read count for a specific book
//    private fun getReadCount(bookId: String): Int {
//        return sharedPreferences.getInt(bookId, 0)
//    }

    // Function to retrieve all book statistics
    private fun getIdToCount(): Map<String, Int> {
        return sharedPreferences.all
            .filterValues { it is Int } // Filter only integer values
            .mapValues { it.value as Int }
    }

    fun getTitleToCount(): Map<String, Int> {
        return getIdToCount().mapNotNull { (id, count) ->
            idToTitle[id]?.let { title -> title to count }
        }.toMap()
    }
}