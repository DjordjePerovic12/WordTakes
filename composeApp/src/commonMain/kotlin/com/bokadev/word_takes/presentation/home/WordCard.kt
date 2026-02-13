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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bokadev.word_takes.core.utils.formatCreatedAt
import com.bokadev.word_takes.core.utils.reactionsToGradient
import com.bokadev.word_takes.data.remote.dto.RateWordRequestDto
import com.bokadev.word_takes.data.remote.dto.ReactionRequestDto
import com.bokadev.word_takes.domain.model.Reactions
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
    wordItem: WordItem,
    onRateClick: (String) -> Unit
) {
    val votesCount =
        wordItem.reactions.bad + wordItem.reactions.good + wordItem.reactions.awful + wordItem.reactions.amazing + wordItem.reactions.skipped

    val decideLabel = when (votesCount) {
        1 -> {
            if (wordItem.myReaction == ReactionRequestDto.SKIPPED.name.lowercase()) "No one rated the word yet"
            else "You are the only one that rated the word"
        }

        2 -> if (wordItem.myReaction == ReactionRequestDto.SKIPPED.name.lowercase()) "1 person rated the word" else if(wordItem.reactions.skipped == 1) "You are the only one that rated the word" else
            "You and 1 other person rated the word"

        else -> if (wordItem.myReaction == ReactionRequestDto.SKIPPED.name.lowercase()) "${votesCount - wordItem.reactions.skipped - 1}  people rated this word" else
            "You and ${votesCount - wordItem.reactions.skipped - 1}  others rated this word"
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (wordItem.myReaction == null || (wordItem.myReaction == ReactionRequestDto.SKIPPED.name.lowercase() && votesCount == 0))
//                    Brush.linearGradient(
//                        colors = listOf(
//                            WordTakesTheme.colors.wordTakesWhite.copy(.2f),
//                            WordTakesTheme.colors.wordTakesWhite.copy(.4f),
//                            WordTakesTheme.colors.wordTakesWhite.copy(.6f),
//                        )
//                    )
                    SolidColor(WordTakesTheme.colors.wordTakesWhite.copy(.95f))
                else
                    reactionsToGradient(
                        reactions = wordItem.reactions
                    )
            )
            .border(
                shape = RoundedCornerShape(20.dp),
                width = 2.dp,
                brush = if (votesCount == 0) SolidColor(WordTakesTheme.colors.wordTakesOrange) else
                    reactionsToGradient(
                        reactions = wordItem.reactions
                    )
            )

            .padding(
                horizontal = 15.dp,
                vertical = 25.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = wordItem.name,
                    color = WordTakesTheme.colors.backgroundPrimary,
                    style = WordTakesTheme.typogrpahy.geistBold18
                )

                Text(
                    text = wordItem.createdAtIso.formatCreatedAt(),
                    color = WordTakesTheme.colors.backgroundPrimary,
                    style = WordTakesTheme.typogrpahy.geistLight14
                )
            }

            if (wordItem.myReaction == null)
                Button(
                    onClick = {
                        onRateClick(ReactionRequestDto.SKIPPED.name.lowercase())
                    },
                    modifier = Modifier.wrapContentWidth()
                        .height(45.dp)
                        .clip(
                            RoundedCornerShape(
                                8.dp
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = WordTakesTheme.colors.backgroundPrimary,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WordTakesTheme.colors.wordTakesWhite.copy(.5f),
                    ),
                    content = {
                        Text(
                            text = "SKIP RATING",
                            color = WordTakesTheme.colors.primaryText,
                            style = WordTakesTheme.typogrpahy.geistSemiBold14
                        )
                    }
                )
        }


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


        when (wordItem.myReaction) {
            null -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            onRateClick(ReactionRequestDto.GOOD.name.lowercase())
                        },
                        modifier = Modifier.wrapContentWidth()
                            .height(45.dp)
                            .clip(
                                RoundedCornerShape(
                                    8.dp
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = WordTakesTheme.colors.backgroundPrimary,
                                shape = RoundedCornerShape(8.dp)
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
                            onRateClick(ReactionRequestDto.AMAZING.name.lowercase())

                        },
                        modifier = Modifier.wrapContentWidth()
                            .height(45.dp)
                            .clip(
                                RoundedCornerShape(
                                    8.dp
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = WordTakesTheme.colors.backgroundPrimary,
                                shape = RoundedCornerShape(8.dp)
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
                            onRateClick(ReactionRequestDto.BAD.name.lowercase())

                        },
                        modifier = Modifier.wrapContentWidth()
                            .height(45.dp)
                            .clip(
                                RoundedCornerShape(
                                    8.dp
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = WordTakesTheme.colors.backgroundPrimary,
                                shape = RoundedCornerShape(8.dp)
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
                            onRateClick(ReactionRequestDto.AWFUL.name.lowercase())

                        },
                        modifier = Modifier.wrapContentWidth()
                            .height(45.dp)
                            .clip(
                                RoundedCornerShape(
                                    8.dp
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = WordTakesTheme.colors.backgroundPrimary,
                                shape = RoundedCornerShape(8.dp)
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

            else -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(WordTakesTheme.colors.backgroundPrimary)
                        .border(
                            shape = RoundedCornerShape(20.dp),
                            width = 2.dp,
                            brush = if (votesCount == 0) SolidColor(WordTakesTheme.colors.wordTakesOrange) else
                                reactionsToGradient(
                                    reactions = wordItem.reactions
                                )
                        )
                        .padding(15.dp)

                ) {

                    Text(
                        decideLabel,
                        color = WordTakesTheme.colors.wordTakesWhite,
                        style = WordTakesTheme.typogrpahy.geistSemiBold14
                    )
                }
            }
        }
    }
}
