package com.leo.unipiaudiostories.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.leo.unipiaudiostories.R

// Define the font family
val comicSansFontFamily = FontFamily(
    Font(R.font.comic_sans, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = comicSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
//        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = comicSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
//        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = comicSansFontFamily,
        fontWeight = FontWeight.W900,
        fontSize = 44.sp,
        //lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = comicSansFontFamily,
        fontWeight = FontWeight.W900,
        fontSize = 32.sp,
        //lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    displayMedium = TextStyle(
        fontFamily = comicSansFontFamily,
        fontSize = 24.sp,
        lineHeight = 30.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontSize = 24.sp,
        lineHeight = 30.sp,
    ),
    /*
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)