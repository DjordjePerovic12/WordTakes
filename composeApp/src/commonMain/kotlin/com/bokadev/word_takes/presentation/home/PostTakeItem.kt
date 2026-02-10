package com.bokadev.word_takes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.bokadev.word_takes.core.components.WordTakesTextField
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme

@Composable
fun PostTakeItem(
    modifier: Modifier = Modifier,
    state: HomeState,
    onWordChange: (TextFieldValue) -> Unit,
    onSubmitClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(WordTakesTheme.colors.wordTakesWhite.copy(.5f))
            .padding(horizontal = 12.dp)
    ) {
        WordTakesTextField(
            modifier = Modifier.fillMaxWidth(.6f),
            value = state.myWord,
            labelText = "Enter a take",
            placeholderText = "Submit a word take...",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            isError = state.myWordError != null,
            errorText = "Word must be at least 3 chars long",
            onValueChange = {
                onWordChange(it)
            }
        )


        when (state.isSubmitInProgress) {
            false -> {
                Button(
                    onClick = {
                        onSubmitClick()
                    },
                    modifier = Modifier.fillMaxWidth()
                        .height(45.dp)
                        .wrapContentWidth()
                        .clip(
                            RoundedCornerShape(
                                20.dp
                            )
                        )
                        .weight(1f),
                    enabled = state.shouldEnableSubmitButton,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WordTakesTheme.colors.wordTakesOrange,
                        disabledContainerColor = WordTakesTheme.colors.wordTakesWhite,
                        contentColor = WordTakesTheme.colors.wordTakesWhite,
                        disabledContentColor = WordTakesTheme.colors.backgroundPrimary.copy(.5f)

                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp
                    ),
                    content = {
                        Text(
                            text = "Submit",
                            style = WordTakesTheme.typogrpahy.geistSemiBold14
                        )
                    }
                )
            }

            true -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    color = WordTakesTheme.colors.wordTakesOrange
                )
            }
        }
    }
}

