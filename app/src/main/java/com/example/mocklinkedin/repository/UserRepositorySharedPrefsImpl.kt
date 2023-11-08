package com.example.mocklinkedin.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mocklinkedin.dto.User
import com.google.gson.Gson

class UserRepositoryImpl(context: Context) : UserRepository {
    private val prefs = context.getSharedPreferences("user_profile_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val userKey = "user"

    override fun getUser(): LiveData<User> {
        val userJson = prefs.getString(userKey, null)
        val userProfile = if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            User(0,"","","",1,1)
        }
        val data = MutableLiveData<User>()
        data.value = userProfile
        return data
    }

    override fun saveUser(user: User) {
        val userProfileJson = gson.toJson(user)
        prefs.edit().putString(userKey, userProfileJson).apply()
    }
}