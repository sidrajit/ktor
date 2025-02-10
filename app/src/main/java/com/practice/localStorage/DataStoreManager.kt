package com.practice.localStorage

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class DataStoreManager(
    val dataStore: DataStore<Preferences>,
    val coRoutineExceptionHandler: CoroutineExceptionHandler,
) {
    /** dataStore*/
    fun <T> saveData(key: Preferences.Key<T>, value: T, valueIs: (T?) -> Unit = {}) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit { preferences ->
                preferences[key] = value
                valueIs(value)
            }
        }
    }

    fun <T> readData(key: Preferences.Key<T>, valueIs: (T?) -> Unit) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit { pref ->
                CoroutineScope(Dispatchers.Main).launch {
                    valueIs(pref[key])
                }
            }
        }
    }


    suspend fun <T> readKey(key: Preferences.Key<T>, valueIs: (T?) -> Unit) {
        dataStore.edit {
            valueIs(it[key])
        }
    }

    inline fun <reified T> saveObject(key: Preferences.Key<String>, value: T) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit { preferences ->
                val data = Json.encodeToJsonElement(value).toString()
                preferences[key] = data
                Log.d("", "saveObject: $data")
            }
        }
    }

    inline fun <reified T> readObject(
        key: Preferences.Key<String>,
        crossinline valueIs: (T?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit {
                val jsonString = it[key]
                val obj: T? = jsonString?.let { data ->
                    try {
                        Json.decodeFromString<T>(data)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                }
                CoroutineScope(Dispatchers.Main).launch {
                    valueIs(obj)
                }
            }
        }
    }

    fun clearDataStore(valueIs: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit { _ ->
                //preferences.clear()
            }
            CoroutineScope(Dispatchers.Main).launch {
                valueIs(true)
            }
        }
    }

    fun clearAllDataStore(valueIs: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit { preferences ->
                preferences.clear()
            }
            CoroutineScope(Dispatchers.Main).launch {
                valueIs(true)
            }
        }
    }


    fun <T> removeKey(key: Preferences.Key<T>, onRemove: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit { preferences ->
                preferences.remove(key)
            }
            CoroutineScope(Dispatchers.Main).launch {
                onRemove(true)
            }
        }
    }

    inline fun readAuthToken(crossinline authToken: (String?) -> Unit) {
        readData(DataStorePreferenceKeys.AUTH_TOKEN) {
            authToken.invoke(it)
        }
    }
}

