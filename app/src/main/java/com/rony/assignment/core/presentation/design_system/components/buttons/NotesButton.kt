package com.rony.assignment.core.presentation.design_system.components.buttons

import android.R
import android.view.RoundedCorner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rony.assignment.core.presentation.design_system.NotesApplicationTheme

enum class NotesButtonStyles {
    PRIMARY,
    SECONDARY;
}

@Composable
fun NotesButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: NotesButtonStyles = NotesButtonStyles.PRIMARY,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null
) {

    val colors =  when(style) {
        NotesButtonStyles.PRIMARY -> {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.6f
                ),
                disabledContentColor = MaterialTheme.colorScheme.outline
            )
        }
        NotesButtonStyles.SECONDARY -> {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                disabledContainerColor = MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.6f
                ),
                disabledContentColor = MaterialTheme.colorScheme.outline
            )
        }
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        colors = colors
    ) {
        Box(
            modifier = Modifier
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(20.dp)
                    .alpha(
                        alpha = if (isLoading) 1f else 0f
                    ),
                strokeWidth = 1.dp,
                color = Color.Black
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .alpha(
                        alpha = if(isLoading) 0f else 1f
                    )
            ) {
                leadingIcon?.let {
                    it.invoke()
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun NotesButtonPrimaryDarkPreview() {
    NotesApplicationTheme(darkTheme = true) {
        NotesButton(
            text = "Log in",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            isLoading = false,
            enabled = true
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun NotesButtonPrimaryLighrPreview() {
    NotesApplicationTheme(darkTheme = false) {
        NotesButton(
            text = "Log in",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            isLoading = false,
            enabled = true
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun NotesButtonPrimaryDisabledDarkPreview() {
    NotesApplicationTheme(darkTheme = true) {
        NotesButton(
            text = "Log in",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            isLoading = false,
            enabled = false
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun NotesButtonPrimaryDisabledLightPreview() {
    NotesApplicationTheme(darkTheme = false) {
        NotesButton(
            text = "Log in",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            isLoading = false,
            enabled = false
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun NotesButtonPrimaryLoadingPreview() {
    NotesApplicationTheme(darkTheme = false) {
        NotesButton(
            text = "Log in",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            isLoading = true,
            enabled = false
        )
    }
}