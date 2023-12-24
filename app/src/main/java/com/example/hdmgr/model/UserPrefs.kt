package com.example.hdmgr.model

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect


class UserPrefs(private val context: Context) {
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val PSSWD = stringPreferencesKey("password")
        val DARKMODE = intPreferencesKey("dark_mode")
    }
    fun getDarkModeState(): Flow<Int>{
        val darkModeFlow: Flow<Int> = context.dataStore.data.map {
            pref -> pref[DARKMODE] ?: 0
        }
        return darkModeFlow
    }
    fun getPasswordState(): Flow<String>{
        val passwordFlow: Flow<String> = context.dataStore.data.map {
            pref -> pref[PSSWD] ?: ""
        }
        return passwordFlow
    }
    suspend fun writeToDarkmodePref(value: Int = 0){
        context.dataStore.edit {
            set -> set[DARKMODE] = value
        }
    }
}