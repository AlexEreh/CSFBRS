package com.alexereh.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import arrow.core.Option
import arrow.core.none
import com.alexereh.model.LoginData
import kotlinx.coroutines.flow.first
import java.io.IOException

class UserDataSource(
    private val dataStore: DataStore<UserData>
) {
    suspend fun setNewUserData(userData: UserData){
        try {
            dataStore.updateData {
                userData
            }
        } catch (ioException: IOException){
            Log.e("DataSource", "Failed to update user data", ioException)
        }
    }
    suspend fun updatePassword(newPassword: String){
        try {
            dataStore.updateData {
                it.copy {
                    password = newPassword
                }
            }
        } catch (ioException: IOException){
            Log.e("DataSource", "Failed to update password", ioException)
        }
    }
    suspend fun setLoggedIn(newLoggedInState: Boolean){
        try {
            dataStore.updateData {
                it.copy {
                    loggedIn = newLoggedInState
                }
            }
        } catch (ioException: IOException){
            Log.e("DataSource", "Failed to change logged in state", ioException)
        }
    }
    suspend fun getLoginAndPassword(): Option<LoginData> {
        return try {
            val data = dataStore.data.first()
            Option(LoginData(data.login, data.password))
        } catch (ioException: IOException) {
            Log.e("DataSource", "Failed to get login data", ioException)
            none()
        }
    }
    suspend fun hasLogin(): Boolean {
        return try {
            dataStore.data.first()
            true
        } catch (e: NoSuchElementException) {
            Log.e("DataSource", "Failed to get login data", e)
            false
        }
    }
}