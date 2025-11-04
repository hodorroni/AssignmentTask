package com.rony.assignment.features.notes.presentation.note

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rony.assignment.R
import com.rony.assignment.core.presentation.design_system.NotesApplicationTheme
import com.rony.assignment.core.presentation.design_system.components.NotesDialog
import com.rony.assignment.core.presentation.design_system.components.buttons.NotesButton
import com.rony.assignment.core.presentation.design_system.components.buttons.NotesButtonStyles
import com.rony.assignment.core.presentation.design_system.components.layouts.NotesAdaptiveLayout
import com.rony.assignment.core.presentation.design_system.components.layouts.NotesSurface
import com.rony.assignment.core.presentation.design_system.components.text_fields.NotesTextField
import com.rony.assignment.core.presentation.utils.ObserveAsEvents
import com.rony.assignment.features.notes.presentation.util.hasLocationPermission
import com.rony.assignment.features.notes.presentation.util.shouldShowLocationPermissionRationale
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteRoot(
    onCancelClicked: () -> Unit,
    viewModel: NoteViewModel = koinViewModel(),
    onSuccessSavingNote: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->
        when(event) {
            NoteEvent.OnSuccessfullySavedNote -> onSuccessSavingNote()
        }
    }

    NoteScreen(
        state = state,
        onAction = { action ->
            when(action) {
                NoteAction.OnCancelClicked -> onCancelClicked()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun NoteScreen(
    state: NoteState,
    onAction: (NoteAction) -> Unit,
) {
    val context = LocalContext.current

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            onAction(NoteAction.OnImageCaptured(it.toString()))
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val hasCourseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        onAction(
            NoteAction.LocationPermissionInfo(
                isAcceptedLocation = hasCourseLocationPermission && hasFineLocationPermission,
                showLocationRationale = showLocationRationale
            )
        )
    }

    //will run once the screen goes into composition.
    LaunchedEffect(key1 = true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()

        onAction(
            NoteAction.LocationPermissionInfo(
                isAcceptedLocation = context.hasLocationPermission(),
                showLocationRationale = showLocationRationale
            )
        )

        if(!showLocationRationale) {
            permissionLauncher.requestLocationPermission(context)
        }
    }

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.shouldShowLocationRationale -> {
            NotesDialog(
                title = "Location permission is needed",
                description = "Location permissions will grant the possibility to watch notes within the Map Mode, otherwise nothing will be shown there",
                primaryButton = {
                    NotesButton(
                        text = "Close",
                        onClick = {
                            onAction(NoteAction.OnDismissedLocationDialog)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                secondaryButton = {},
                onDismiss = {
                    onAction(
                        NoteAction.OnDismissedLocationDialog
                    )
                }
            )
        }
        else -> {
            NotesAdaptiveLayout(
                shouldIncludeVerticalScroll = true,
                modifier = Modifier.fillMaxSize(),
                header = {
                    Spacer(modifier = Modifier.height(64.dp))
                }
            ) {
                val imageModifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = {
                            pickImageLauncher.launch("image/*")
                        }
                    )
                Spacer(modifier = Modifier.height(12.dp))
                if (state.imageUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(state.imageUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Note image",
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier
                            .width(200.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .align(Alignment.CenterHorizontally)
                    )
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(R.drawable.anonymous)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Default image",
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier
                            .width(200.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                NotesTextField(
                    state = state.titleTextFieldState,
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeHolder = if(state.isFromCreate) {
                        "Title"
                    } else null,
                    title = "Title",
                    errorText = state.titleError,
                    isError = state.titleError != null,
                    singleLine = true,
                    enabled = state.isFromCreate || (!state.isEditButtonShown)
                )

                Spacer(modifier = Modifier.height(16.dp))
                NotesTextField(
                    state = state.descriptionTextFieldState,
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeHolder = if(state.isFromCreate) {
                        "Description"
                    } else null,
                    title = "Description",
                    errorText = state.descError,
                    isError = state.descError != null,
                    singleLine = false,
                    enabled = state.isFromCreate || (!state.isEditButtonShown)
                )

                Spacer(modifier = Modifier.height(16.dp))
                if(state.isFromCreate) {
                    IsFromCreateNewNoteButtons(
                        isSaveButtonLoading = state.isSaving,
                        canSave = state.canSave,
                        onCancelClicked = {
                            onAction(NoteAction.OnCancelClicked)
                        },
                        onSaveClicked = {
                            onAction(NoteAction.OnSaveNote)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    DefaultButtons(
                        isEditButtonShown = state.isEditButtonShown,
                        canSave = state.canSave,
                        isSaveButtonLoading = state.isSaving,
                        onCancelClicked = {
                            onAction(NoteAction.OnCancelClicked)
                        },
                        onEditToggled = {
                            onAction(NoteAction.OnEditToggled)
                        },
                        onSaveClicked = {
                            onAction(NoteAction.OnSaveNote)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun DefaultButtons(
    isEditButtonShown: Boolean,
    canSave: Boolean,
    isSaveButtonLoading: Boolean,
    onCancelClicked: () -> Unit,
    onEditToggled: () -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        NotesButton(
            text = "Cancel",
            onClick = onCancelClicked,
            modifier = Modifier.weight(1f),
            style = NotesButtonStyles.SECONDARY
        )

        Spacer(modifier = Modifier.width(16.dp))

        AnimatedVisibility(visible = isEditButtonShown) {
            NotesButton(
                text = "Edit",
                onClick = onEditToggled,
                style = NotesButtonStyles.PRIMARY
            )
        }

        AnimatedVisibility(visible = !isEditButtonShown) {
            NotesButton(
                text = "Save",
                onClick = onSaveClicked,
                style = NotesButtonStyles.PRIMARY,
                enabled = !isSaveButtonLoading && canSave,
                isLoading = isSaveButtonLoading
            )
        }
    }
}

@Composable
fun IsFromCreateNewNoteButtons(
    isSaveButtonLoading: Boolean,
    canSave: Boolean,
    onCancelClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        NotesButton(
            text = "Cancel",
            onClick = onCancelClicked,
            modifier = Modifier
                .weight(1f),
            style = NotesButtonStyles.SECONDARY,
            enabled = true
        )
        Spacer(modifier = Modifier.width(10.dp))
        NotesButton(
            text = "Save",
            onClick = onSaveClicked,
            modifier = Modifier
                .weight(1f),
            style = NotesButtonStyles.PRIMARY,
            enabled = !isSaveButtonLoading && canSave,
            isLoading = isSaveButtonLoading,
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestLocationPermission(
    context: Context
) {
    val hasLocationPermission = context.hasLocationPermission()
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    if(!hasLocationPermission) {
        launch(locationPermissions)
    }
}

@Preview
@Composable
private fun NoteFromCreatePreview() {
    NotesApplicationTheme {
        NoteScreen(
            state = NoteState(
                isFromCreate = true
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun NoteFromEditOrViewPreview() {
    NotesApplicationTheme {
        NoteScreen(
            state = NoteState(
                isFromCreate = false
            ),
            onAction = {}
        )
    }
}