package com.example.todoapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore:DataStore<Preferences> by preferencesDataStore("storage")

class DatastoreManager(private val ctx : Context) {

    private val gson = GsonBuilder().create()

    suspend fun saveElements(data: MutableList<ToDoItem>) {
        for (i in 0 until data.size) {
            ctx.dataStore.edit { pref ->
                pref[stringPreferencesKey("elem$i")] = gson.toJson(data[i])
            }
        }
        ctx.dataStore.edit { pref ->
            pref[intPreferencesKey("size")] = data.size
        }
    }

    suspend fun getElements() : MutableList<ToDoItem> {
        val data : MutableList<ToDoItem> = mutableListOf()
        val prefs = ctx.dataStore.data.first()
        val size = prefs[intPreferencesKey("size")] ?: 0
        var item = ""
        for (i in 0 until size) {
            item = prefs[stringPreferencesKey("elem$i")] ?: ""
            data.add(gson.fromJson(item, ToDoItem::class.java))
        }
        return data
    }
}