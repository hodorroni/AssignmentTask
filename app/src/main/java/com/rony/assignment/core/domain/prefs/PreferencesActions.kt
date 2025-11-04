package com.rony.assignment.core.domain.prefs

interface PreferencesActions {

    fun saveFirstname(firstName: String)
    fun getFirstname(): String?
}