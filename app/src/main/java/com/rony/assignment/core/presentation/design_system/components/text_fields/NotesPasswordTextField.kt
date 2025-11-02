package com.rony.assignment.core.presentation.design_system.components.text_fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rony.assignment.R
import com.rony.assignment.core.presentation.design_system.NotesApplicationTheme

@Composable
fun NotesPasswordTextField(
    state: TextFieldState,
    isPasswordVisible: Boolean,
    onToggleVisibilityClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    title: String? = null,
    errorText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
) {
    TextFieldLayout(
        title = title,
        isError = isError,
        errorText = errorText,
        enabled = enabled,
        modifier = modifier
    ) { styledModifier, interactionSource ->
        BasicSecureTextField(
            state = state,
            modifier = styledModifier,
            enabled = enabled,
            textObfuscationMode = if(isPasswordVisible) {
                TextObfuscationMode.Visible
            } else TextObfuscationMode.Hidden,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if(enabled) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onTertiary
            ),
            interactionSource = interactionSource,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            decorator = { innerBox ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if(state.text.isEmpty() && placeHolder != null) {
                            Text(
                                text = placeHolder,
                                color = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.8f
                                ),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        innerBox()
                    }
                    Icon(
                        imageVector = if (isPasswordVisible) {
                            ImageVector.vectorResource(id = R.drawable.eye_off_icon)
                        } else {
                            ImageVector.vectorResource(id = R.drawable.eye_icon)
                        },
                        contentDescription = if(isPasswordVisible) {
                            "Hide password"
                        } else "Show password",
                        tint = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.8f
                        ),
                        modifier = Modifier
                            .size(26.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(
                                    bounded = false,
                                    radius = 26.dp
                                ),
                                onClick = onToggleVisibilityClick
                            )
                    )
                }
            }
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun NotesPasswordTextFieldWithStatePreview() {
    NotesApplicationTheme {
        NotesPasswordTextField(
            state = TextFieldState("hodorroni@gmail.com"),
            isPasswordVisible = true,
            onToggleVisibilityClick = {},
            modifier = Modifier.fillMaxWidth(),
            placeHolder = null,
            title = "Password",
            errorText = null,
            isError = false,
            enabled = true
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun NotesPasswordTextFieldWithStateDarkPreview() {
    NotesApplicationTheme(darkTheme = true) {
        NotesPasswordTextField(
            state = TextFieldState(),
            isPasswordVisible = true,
            onToggleVisibilityClick = {},
            modifier = Modifier.fillMaxWidth(),
            placeHolder = "password",
            title = "Password",
            errorText = null,
            isError = false,
            enabled = true
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun NotesPasswordTextFieldPlaceHolderPreview() {
    NotesApplicationTheme {
        NotesPasswordTextField(
            state = TextFieldState(),
            isPasswordVisible = true,
            onToggleVisibilityClick = {},
            modifier = Modifier.fillMaxWidth(),
            placeHolder = "Password",
            title = "Password",
            errorText = null,
            isError = false,
            enabled = true
        )
    }
}