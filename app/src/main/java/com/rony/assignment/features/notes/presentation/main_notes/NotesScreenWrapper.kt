package com.rony.assignment.features.notes.presentation.main_notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rony.assignment.R
import com.rony.assignment.core.presentation.design_system.NotesApplicationTheme
import com.rony.assignment.core.presentation.design_system.components.layouts.NotesSurface
import com.rony.assignment.core.presentation.utils.DeviceConfiguration
import com.rony.assignment.core.presentation.utils.currentDeviceConfig
import com.rony.assignment.features.notes.domain.NoteUi

@Composable
fun NotesScreenWrapper(
    state: NotesState,
    onAction: (NotesAction) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val currentDevice = currentDeviceConfig()
    val modifier = when(currentDevice) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            Modifier
        }
        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            Modifier
                .padding(horizontal = 16.dp)
                .padding(
                    WindowInsets.displayCutout
                        .union(WindowInsets.navigationBars)
                        .asPaddingValues()
                )
        }
        else -> {
            Modifier
                .widthIn(max = 500.dp)
                .fillMaxHeight()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = when(currentDevice) {
            DeviceConfiguration.MOBILE_LANDSCAPE -> Alignment.TopCenter
            DeviceConfiguration.MOBILE_PORTRAIT -> Alignment.TopCenter
            else -> Alignment.Center
        }
    ) {
        NotesSurface(
            modifier = modifier,
            shouldIncludeVerticalScroll = false,
            logoutHeader = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.height(55.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_logo),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(40.dp),
                            tint = Color.Unspecified
                        )

                        Text(
                            text = stringResource(R.string.log_out),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clickable {
                                    onAction(NotesAction.OnLogoutClick)
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))
                }
            },
        ) {
            when {
                state.isLoadingNotes -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                state.notes.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.empty_notes),
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp)
                        )
                    }
                }
                else -> {
                    TabLayout(
                        items = state.tabList,
                        onTabSelected = {
                            onAction(NotesAction.OnNoteTabSelected(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    content()
                }
            }
        }
    }
}

@Composable
fun TabLayout(
    items: List<TabItem>,
    onTabSelected: (ScreenMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.2f))
            .padding(vertical = 8.dp)
    ) {
        items.forEach { tab ->
            TabItem(
                text = stringResource(id = tab.title),
                isSelected = tab.isSelected,
                onClick = { onTabSelected(tab.screenMod) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        val textColor = if (isSelected) {
            MaterialTheme.colorScheme.onBackground
        } else {
            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = if (isSelected) 16.sp else 14.sp
            )

            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth(0.5f)
                    .background(
                        if (isSelected)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        else Color.Transparent
                    )
                    .graphicsLayer {
                        translationY = 4f
                    }
            )
        }
    }
}

@Preview
@Composable
private fun NotesPreviewWrapper() {
    NotesApplicationTheme {
        NotesScreenWrapper(
            state = NotesState(
                notes = listOf(
                    NoteUi()
                )
            ),
            onAction = {},
            content = {}
        )
    }
}