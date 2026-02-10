package com.bokadev.word_takes.core.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme

@Composable
fun WordTakesTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    labelText: String,
    placeholderText: String,
    isReadOnly: Boolean = false,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    errorText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (TextFieldValue) -> Unit,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) {
    Column {
        TextField(
            visualTransformation = visualTransformation,
            value = value,
            onValueChange = { onValueChange(it) },
            readOnly = isReadOnly,
            label = {
                Text(
                    text = labelText,
                    color = WordTakesTheme.colors.wordTakesWhite,
                    style = WordTakesTheme.typogrpahy.geistRegular12
                )
            },
            placeholder = {
                Text(
                    text = placeholderText,
                    color = WordTakesTheme.colors.wordTakesWhite,
                    style = WordTakesTheme.typogrpahy.geistRegular12
                )
            },
            isError = isError,
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
                capitalization = capitalization
            ),
            modifier = modifier
                .onFocusChanged { focusState ->
                    onFocusChanged?.invoke(focusState.isFocused)
                }
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (isError) WordTakesTheme.colors.errorRed else WordTakesTheme.colors.wordTakesWhite,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = WordTakesTheme.colors.wordTakesWhite.copy(.5f),
                unfocusedContainerColor = WordTakesTheme.colors.wordTakesWhite.copy(.5f),
                errorContainerColor = WordTakesTheme.colors.wordTakesWhite,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedTextColor = WordTakesTheme.colors.wordTakesWhite,
                unfocusedTextColor = WordTakesTheme.colors.wordTakesWhite.copy(.5f),
                cursorColor = WordTakesTheme.colors.wordTakesWhite,
            )
        )

        if (isError && errorText != null) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = errorText,
                color = WordTakesTheme.colors.errorRed,
                style = WordTakesTheme.typogrpahy.geistRegular12,
                modifier = modifier,
            )
        }
    }
}