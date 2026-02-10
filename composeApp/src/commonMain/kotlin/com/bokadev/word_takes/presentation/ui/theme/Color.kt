package com.bokadev.word_takes.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class WordTakesColors(
    val backgroundPrimary: Color,
    val wordTakesWhite: Color,
    val wordTakesOrange: Color,
    val colorBlack: Color,
    val colorWhite: Color,
    val colorDarkGrey: Color,
    val colorLightGrey: Color,
    val colorAccentOrange: Color,
    val borderOrange: Color,
    val borderGray: Color,
    val textFieldLabel: Color,
    val horizontalDividerColor: Color,
    val checkoutFormBackground: Color,
    val errorRed: Color,
    val primary500: Color,
    val surface200: Color,
    val red: Color,
    val badgeRed: Color,
)

val LocalWordTakesColors = staticCompositionLocalOf<WordTakesColors> {
    error("WordTakesColors not provided")
}

// You can keep this as a regular val, no @Composable needed
val wordTakesColors = WordTakesColors(
    backgroundPrimary = Color(0xFF1B3C53),
    wordTakesWhite = Color(0XFFE3E3E3),
    wordTakesOrange = Color(0XFFF8843F),
    colorBlack = Color(0xFF000000),
    colorWhite = Color(0xFFFFFFFF),
    colorDarkGrey = Color(0xFF404041),
    colorLightGrey = Color(0xFFECECEC),
    colorAccentOrange = Color(0xFFF7941D),
    borderOrange = Color(0xFFFFB052),
    borderGray = Color(0xFFC6C6C6),
    textFieldLabel = Color(0xFF717171),
    horizontalDividerColor = Color(0x1A404041),
    checkoutFormBackground = Color(0x08404041),
    errorRed = Color(0xFFC13515),
    primary500 = Color(0xFF10B981),
    surface200 = Color(0xFFE4E4E7),
    red = Color(0xFFFF3758),
    badgeRed = Color(0xFFF71D4C),
)