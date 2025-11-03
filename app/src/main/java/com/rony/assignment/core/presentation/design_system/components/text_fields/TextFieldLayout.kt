package com.rony.assignment.core.presentation.design_system.components.text_fields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldLayout(
    title: String?,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
    enabled: Boolean = true,
    textFField: @Composable (Modifier, MutableInteractionSource) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val textFieldModifier = Modifier
        .fillMaxWidth()
        .background(
            color = when {
                isFocused -> MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.05f
                )
                enabled -> MaterialTheme.colorScheme.surface
                else -> MaterialTheme.colorScheme.secondary
            },
            shape = RoundedCornerShape(12.dp)
        )
        .border(
            width = 1.dp,
            color = when {
                isError -> MaterialTheme.colorScheme.error
                isFocused -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outline
            },
            shape = RoundedCornerShape(12.dp)
        )
        .padding(12.dp)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        title?.let {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.8f
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        textFField(textFieldModifier, interactionSource)

        errorText?.let {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = errorText,
                color = if(isError) {
                    MaterialTheme.colorScheme.error
                } else MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}