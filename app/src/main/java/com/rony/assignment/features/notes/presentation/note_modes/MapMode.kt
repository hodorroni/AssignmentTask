package com.rony.assignment.features.notes.presentation.note_modes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.rony.assignment.R
import com.rony.assignment.features.notes.domain.NoteUi
import com.rony.assignment.features.notes.domain.map.MapData

@Composable
fun MapMode(
    items: List<MapData>,
    onNoteClicked:(Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mapStyle = remember {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            items.firstOrNull()?.let { LatLng(it.latitude ?: 0.0, it.longitude ?: 0.0) }
                ?: LatLng(0.0, 0.0),
            10f
        )
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapStyleOptions = mapStyle)
    ) {
        items.forEach { note ->
            val lat = note.latitude ?: return@forEach
            val lon = note.longitude ?: return@forEach

            Marker(
                state = MarkerState(position = LatLng(lat, lon)),
                title = note.title ?: "",
                snippet = note.description ?: "",
                onClick = {
                    onNoteClicked(note.id)
                    false
                }
            )
        }
    }
}