package com.rony.assignment.features.notes.presentation

import com.rony.assignment.features.notes.domain.NoteUi

data class NotesState(
    val currentScreenMode: ScreenMode = ScreenMode.LIST_MODE,
    val isLoadingNotes: Boolean = false,
    val notes: List<NoteUi> = emptyList()
) {
    val tabList = listOf<TabItem>(
        TabItem(
            title = "List Mode",
            screenMod = ScreenMode.LIST_MODE,
            isSelected = this.currentScreenMode == ScreenMode.LIST_MODE
        ),
        TabItem(
            title = "Map Mode",
            screenMod = ScreenMode.MAP_MODE,
            isSelected = this.currentScreenMode == ScreenMode.MAP_MODE
        )
    )
}

data class TabItem(
    val title: String,
    val screenMod: ScreenMode,
    val isSelected: Boolean
)

enum class ScreenMode{
    LIST_MODE,
    MAP_MODE;
}