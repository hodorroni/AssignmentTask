package com.rony.assignment.core.presentation.design_system.components.text_fields

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NotesTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    title: String? = null,
    errorText: String? = null,
    isError: Boolean = false,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    TextFieldLayout(
        title = title,
        isError = isError,
        errorText = errorText,
        enabled = enabled,
        modifier = modifier
    ) { styledModifier, interactionSource ->
        BasicTextField(
            state = state,
            enabled = enabled,
            lineLimits = if(singleLine) {
                TextFieldLineLimits.SingleLine
            } else TextFieldLineLimits.Default,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if(enabled) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onTertiary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
            interactionSource = interactionSource,
            modifier = styledModifier,
            decorator = { innerBox ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if(state.text.isEmpty() && placeHolder != null) {
                        Text(
                            text = placeHolder,
                            color = MaterialTheme.colorScheme.onPrimary.copy(
                                alpha = 0.8f
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    innerBox()
                }
            }
        )
    }
}