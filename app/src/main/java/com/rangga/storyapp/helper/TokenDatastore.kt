package com.rangga.storyapp.helper
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rangga.storyapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class TokenDatastore (context: Context){
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    companion object {
        const val USER_TOKEN = "user_token"
    }
    suspend fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(SessionManager.USER_TOKEN, token)
        editor.apply()
    }


    suspend fun clearToken() {
        val editor = prefs.edit()
        editor.putString(SessionManager.USER_TOKEN, "")
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(SessionManager.USER_TOKEN, null)
    }


}