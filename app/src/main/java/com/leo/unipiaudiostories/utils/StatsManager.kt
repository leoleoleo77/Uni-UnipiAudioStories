package com.leo.unipiaudiostories.utils

import android.content.Context
import android.content.SharedPreferences

class StatsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.USER_STATS, Context.MODE_PRIVATE)

    // Function to initialize statistics for new book IDs
    fun initializeBookStats(bookIds: List<String>) {
        val editor = sharedPreferences.edit()

        for (bookId in bookIds) {
            // Check if the book ID already exists
            if (!sharedPreferences.contains(bookId)) {
                // If it's a new book ID, initialize it with 0 reads
                editor.putInt(bookId, 0)
            }
        }

        // Commit the changes
        editor.apply()
    }

    // Function to increment the read count for a book
    fun incrementReadCount(bookId: String) {
        val currentCount = sharedPreferences.getInt(bookId, 0)
        sharedPreferences.edit().putInt(bookId, currentCount + 1).apply()
    }

    // Function to retrieve the read count for a specific book
    fun getReadCount(bookId: String): Int {
        return sharedPreferences.getInt(bookId, 0)
    }

    // Function to retrieve all book statistics
    fun getAllStats(): Map<String, Int> {
        return sharedPreferences.all
            .filterValues { it is Int } // Filter only integer values
            .mapValues { it.value as Int }
    }
}