package com.bokadev.word_takes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bokadev.word_takes.domain.model.WordItem
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme

@Composable
fun WordCard(
    modifier: Modifier = Modifier,
    wordItem: WordItem
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(WordTakesTheme.colors.wordTakesWhite.copy(.7f))
            .padding(
                horizontal = 15.dp,
                vertical = 25.dp
            )
    ) {
        Text(
            text = wordItem.name,
            color = WordTakesTheme.colors.backgroundPrimary,
            style = WordTakesTheme.typogrpahy.geistBold18
        )

        Text(
            text = wordItem.createdAtIso,
            color = WordTakesTheme.colors.backgroundPrimary,
            style = WordTakesTheme.typogrpahy.geistLight14
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = wordItem.word,
                color = WordTakesTheme.colors.backgroundPrimary,
                style = WordTakesTheme.typogrpahy.geistSemiBold24,
                letterSpacing = 20.sp
            )
        }
    }
}
