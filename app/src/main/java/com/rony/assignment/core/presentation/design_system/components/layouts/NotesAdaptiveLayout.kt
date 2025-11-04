package com.rony.assignment.core.presentation.design_system.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.rony.assignment.core.presentation.utils.DeviceConfiguration
import com.rony.assignment.core.presentation.utils.currentDeviceConfig
import timber.log.Timber

@Composable
fun NotesAdaptiveLayout(
    shouldIncludeVerticalScroll: Boolean = true,
    header: @Composable ColumnScope.() -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val currentConfig = currentDeviceConfig()

    when(currentConfig) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            NotesSurface(
                modifier = modifier,
                shouldIncludeVerticalScroll = shouldIncludeVerticalScroll,
                header = header,
                content = content
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            Column (
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(
                        WindowInsets.displayCutout
                            .union(WindowInsets.navigationBars)
                            .asPaddingValues()
                    )
                    .padding(vertical = 20.dp),
            ) {
                header()
                NotesSurface(
                    shouldIncludeVerticalScroll = shouldIncludeVerticalScroll,
                    shouldSetBottomCardRounded = true,
                    modifier = Modifier
                ) {
                    content()
                }
            }
        }
        else -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                header()
                Column(
                    modifier = Modifier
                        .widthIn(max = 470.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(all = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    content()
                }
            }
        }
    }
}