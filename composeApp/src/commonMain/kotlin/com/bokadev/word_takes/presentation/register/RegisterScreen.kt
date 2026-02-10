package com.bokadev.word_takes.presentation.register


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bokadev.word_takes.core.components.WordTakesTextField
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.core.utils.noRippleClickable
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import word_takes.composeapp.generated.resources.Res
import word_takes.composeapp.generated.resources.icon_eye
import word_takes.composeapp.generated.resources.icon_eye_off

@Composable
fun RegisterScreen(
    showSnackBar: (String) -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .background(WordTakesTheme.colors.backgroundPrimary)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(.15f))

        Text(
            text = "Create an account \n and post your first take!",
            style = WordTakesTheme.typogrpahy.geistMedium24,
            color = WordTakesTheme.colors.wordTakesWhite,
            textAlign = TextAlign.Center
        )

        WordTakesTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            labelText = "Full name",
            placeholderText = "John Doe",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            isError = state.isNameValid && state.nameError != null,
            errorText = "Please enter a valid name",
            onValueChange = {
                viewModel.onEvent(RegisterEvent.OnNameChange(it))
            }
        )

        WordTakesTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            labelText = "Email",
            placeholderText = "mail@email.com",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            isError = state.isEmailValid && state.emailError != null,
            errorText = "Please enter a valid email",
            onValueChange = {
                viewModel.onEvent(RegisterEvent.OnEmailChange(it))
            }
        )

        WordTakesTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            labelText = "Password",
            placeholderText = "********",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            isError = state.isPasswordValid && state.passwordError != null,
            errorText = "Password must contain at least 8 characters",
            onValueChange = {
                viewModel.onEvent(RegisterEvent.OnPasswordChange(it))
            },
            trailingIcon = {
                Icon(
                    imageVector = if (!state.isPasswordVisible) vectorResource(Res.drawable.icon_eye) else vectorResource(
                        Res.drawable.icon_eye_off
                    ),
                    contentDescription = null,
                    tint = WordTakesTheme.colors.wordTakesWhite,
                    modifier = Modifier.noRippleClickable {
                        viewModel.onEvent(RegisterEvent.TogglePasswordVisibility(!state.isPasswordVisible))
                    })
            },
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Button(
            onClick = {
                viewModel.onEvent(RegisterEvent.OnRegisterClick)
            },
            modifier = Modifier.fillMaxWidth()
                .height(45.dp)
                .clip(
                    RoundedCornerShape(
                        20.dp
                    )
                ),
            enabled = state.shouldEnableButton,
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
                    text = "Create account",
                    style = WordTakesTheme.typogrpahy.geistSemiBold14
                )
            }
        )
    }
}