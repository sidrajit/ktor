package com.practice.localStorage

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferenceKeys {
    private const val APPLICATION_ID = "com.practice.ktorPractice"
    val DATA_STORE_NAME by lazy { "ktorClient" }
    val AUTH_TOKEN by lazy { stringPreferencesKey("${APPLICATION_ID}_AUTH_TOKEN") }
    val MODEL by lazy { stringPreferencesKey("${APPLICATION_ID}_MODEL") }
}