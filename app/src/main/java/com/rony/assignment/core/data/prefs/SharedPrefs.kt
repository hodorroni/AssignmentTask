package com.rony.assignment.core.data.prefs

import android.content.Context
import com.rony.assignment.core.domain.prefs.PreferencesActions
import com.rony.assignment.core.domain.prefs.SharedPrefKeys

class SharedPrefs(
    private val context: Context
): PreferencesActions {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    override fun saveFirstname(firstName: String) {
        prefs.edit()
            .putString(SharedPrefKeys.USER_NAME, firstName)
            .apply()
    }

    override fun getFirstname(): String? = prefs.getString(SharedPrefKeys.USER_NAME, null)
}