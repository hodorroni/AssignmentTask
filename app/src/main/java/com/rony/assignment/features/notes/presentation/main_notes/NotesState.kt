package com.rony.assignment.features.notes.presentation.main_notes

import com.rony.assignment.R
import com.rony.assignment.features.notes.domain.NoteUi
import com.rony.assignment.features.notes.domain.map.MapData

data class NotesState(
    val currentScreenMode: ScreenMode = ScreenMode.LIST_MODE,
    val userFullName: String? = null,
    val isLoadingNotes: Boolean = true,
    val notes: List<NoteUi> = emptyList()
) {
    val tabList = listOf<TabItem>(
        TabItem(
            title = R.string.list_mode,
            screenMod = ScreenMode.LIST_MODE,
            isSelected = this.currentScreenMode == ScreenMode.LIST_MODE
        ),
        TabItem(
            title = R.string.map_mode,
            screenMod = ScreenMode.MAP_MODE,
            isSelected = this.currentScreenMode == ScreenMode.MAP_MODE
        )
    )

    val mapItemsWithCoordinates = notes
        .mapNotNull { noteUi ->
            if(noteUi.latitude == null || noteUi.longitude == null) {
                return@mapNotNull null
            }
            MapData(
                id = noteUi.id,
                title = noteUi.title,
                description = noteUi.description,
                latitude = noteUi.latitude,
                longitude = noteUi.longitude
            )
        }
}

data class TabItem(
    val title: Int,
    val screenMod: ScreenMode,
    val isSelected: Boolean
)

enum class ScreenMode{
    LIST_MODE,
    MAP_MODE;
}