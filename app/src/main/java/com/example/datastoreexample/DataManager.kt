package com.example.datastoreexample

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataManager(context: Context) {

    private val dataStore = context.createDataStore("data_prefs")

    companion object{
        val USER_NAME = preferencesKey<String>("USER_NAME")
        val USER_GITHUB = preferencesKey<String>("USER_GITHUB")
        val USER_NO = preferencesKey<Int>("USER_NO")
    }

    suspend fun storeData(name:String, github:String, no:Int){
        dataStore.edit {
            it[USER_NAME] = name
            it[USER_GITHUB] = github
            it[USER_NO] = no
        }
    }

    val userNameFlow:Flow<String> = dataStore.data.map {
        it[USER_NAME] ?: ""
    }

    val userGithubFlow:Flow<String> = dataStore.data.map {
        it[USER_GITHUB] ?: ""
    }

    val userNo: Flow<Int> = dataStore.data.map {
        it[USER_NO] ?: -1
    }

}