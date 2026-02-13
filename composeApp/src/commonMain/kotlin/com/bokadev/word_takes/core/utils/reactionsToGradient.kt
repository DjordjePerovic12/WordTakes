package com.bokadev.word_takes.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.bokadev.word_takes.data.remote.dto.RateWordRequestDto
import com.bokadev.word_takes.domain.model.Reactions
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme

@Composable
fun reactionsToGradient(
    reactions: Reactions,
    fallback: Color = Color(0xFFE0E0E0) // no votes case
): Brush {


    val entries = listOf(
        reactions.good to WordTakesTheme.colors.wordTakesGood,
        reactions.amazing to WordTakesTheme.colors.wordTakesAmazing,
        reactions.bad to WordTakesTheme.colors.wordTakesBad,
        reactions.awful to WordTakesTheme.colors.wordTakesAwful
    ).filter { it.first > 0 }

    // No votes → neutral background
    if (entries.isEmpty()) {
        return Brush.linearGradient(listOf(fallback, fallback))
    }

    // Single vote type → solid color
    if (entries.size == 1) {
        val color = entries.first().second
        return Brush.linearGradient(listOf(color, color))
    }

    val totalVotes = entries.sumOf { it.first }

    var currentStop = 0f
    val colorStops = mutableListOf<Pair<Float, Color>>()

    entries.forEachIndexed { index, (count, color) ->
        val fraction = count.toFloat() / totalVotes

        // start stop
        colorStops += currentStop to color

        currentStop += fraction

        // end stop (avoid float drift on last item)
        if (index == entries.lastIndex) {
            colorStops += 1f to color
        }
    }

    return Brush.linearGradient(
        colorStops = colorStops.toTypedArray()
    )
}
