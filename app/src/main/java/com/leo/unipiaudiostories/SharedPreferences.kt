package com.leo.unipiaudiostories

import android.content.Context
import android.content.SharedPreferences
import com.leo.unipiaudiostories.utils.AppConstants

fun getAppLanguage(context: Context): String {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.APP_PREFERENCES, Context.MODE_PRIVATE)


    val language = sharedPreferences
        .getString(AppConstants.SP_LANGUAGE, AppConstants.ENGLISH) ?: AppConstants.ENGLISH

    return language
}

fun updateLanguage(context: Context, language: String) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.APP_PREFERENCES, Context.MODE_PRIVATE)

    sharedPreferences.edit()
        .putString(AppConstants.SP_LANGUAGE, language)
        .apply()
}