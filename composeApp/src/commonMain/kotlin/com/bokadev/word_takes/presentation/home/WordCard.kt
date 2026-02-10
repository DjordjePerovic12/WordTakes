package com.bokadev.word_takes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bokadev.word_takes.core.utils.formatCreatedAt
import com.bokadev.word_takes.domain.model.WordItem
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme
import org.jetbrains.compose.resources.vectorResource
import word_takes.composeapp.generated.resources.Res
import word_takes.composeapp.generated.resources.ic_angry
import word_takes.composeapp.generated.resources.ic_thumbs_down
import word_takes.composeapp.generated.resources.ic_thumbs_up
import word_takes.composeapp.generated.resources.party_popper

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
            .background(WordTakesTheme.colors.backgroundSecondary)
            .border(
                shape = RoundedCornerShape(20.dp),
                width = 2.dp,
                color = WordTakesTheme.colors.wordTakesOrange
            )

            .padding(
                horizontal = 15.dp,
                vertical = 25.dp
            )
    ) {
        Text(
            text = wordItem.name,
            color = WordTakesTheme.colors.wordTakesWhite,
            style = WordTakesTheme.typogrpahy.geistBold18
        )

        Text(
            text = wordItem.createdAtIso.formatCreatedAt(),
            color = WordTakesTheme.colors.wordTakesWhite,
            style = WordTakesTheme.typogrpahy.geistLight14
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = wordItem.word,
                color = WordTakesTheme.colors.wordTakesWhite,
                style = WordTakesTheme.typogrpahy.geistSemiBold24,
                letterSpacing = 20.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                },
                modifier = Modifier.wrapContentWidth()
                    .height(45.dp)
                    .clip(
                        RoundedCornerShape(
                            8.dp
                        )
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WordTakesTheme.colors.wordTakesGood,
                    contentColor = WordTakesTheme.colors.wordTakesWhite,
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp
                ),
                content = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_thumbs_up),
                        contentDescription = null,
                        tint = WordTakesTheme.colors.backgroundSecondary
                    )
                }
            )

            Button(
                onClick = {
                },
                modifier = Modifier.wrapContentWidth()
                    .height(45.dp)
                    .clip(
                        RoundedCornerShape(
                            8.dp
                        )
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WordTakesTheme.colors.wordTakesAmazing,
                    contentColor = WordTakesTheme.colors.wordTakesWhite,
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp
                ),
                content = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.party_popper),
                        contentDescription = null,
                        tint = WordTakesTheme.colors.backgroundSecondary
                    )
                }
            )

            Button(
                onClick = {
                },
                modifier = Modifier.wrapContentWidth()
                    .height(45.dp)
                    .clip(
                        RoundedCornerShape(
                            8.dp
                        )
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WordTakesTheme.colors.wordTakesBad,
                    contentColor = WordTakesTheme.colors.wordTakesWhite,
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp
                ),
                content = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_thumbs_down),
                        contentDescription = null,
                        tint = WordTakesTheme.colors.backgroundSecondary
                    )
                }
            )

            Button(
                onClick = {
                },
                modifier = Modifier.wrapContentWidth()
                    .height(45.dp)
                    .clip(
                        RoundedCornerShape(
                            8.dp
                        )
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WordTakesTheme.colors.wordTakesAwful,
                    contentColor = WordTakesTheme.colors.wordTakesWhite,
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp
                ),
                content = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_angry),
                        contentDescription = null,
                        tint = WordTakesTheme.colors.backgroundSecondary
                    )
                }
            )
        }
    }
}
