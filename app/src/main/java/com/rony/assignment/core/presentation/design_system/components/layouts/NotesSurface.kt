package com.rony.assignment.core.presentation.design_system.components.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotesSurface(
    modifier: Modifier = Modifier,
    shouldIncludeVerticalScroll: Boolean = true,
    shouldSetBottomCardRounded: Boolean = false,
    header: @Composable ColumnScope.() -> Unit = {},
    logoutHeader: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = if(shouldSetBottomCardRounded) {
        RoundedCornerShape(15.dp)
    } else {
        RoundedCornerShape(
            topStart = 15.dp,
            topEnd = 15.dp
        )
    }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            logoutHeader?.invoke()
            header()
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                shape = shape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .then(
                            if (shouldIncludeVerticalScroll) {
                                Modifier.verticalScroll(rememberScrollState())
                            } else {
                                Modifier
                            }
                        )
                        .then(
                            if(shouldIncludeVerticalScroll) {
                                Modifier.padding(bottom = 15.dp)
                            } else Modifier
                        )
                ) {
                    content()
                }
            }
        }
    }
}