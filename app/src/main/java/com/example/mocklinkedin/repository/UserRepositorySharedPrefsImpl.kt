package com.example.mocklinkedin.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mocklinkedin.dto.Attachment
import com.example.mocklinkedin.dto.Avatar
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.dto.User
import com.example.mocklinkedin.enumeration.AttachmentType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserRepositorySharedPrefsImpl(context: Context) : UserRepository {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("user_profile_prefs", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val userKey = "user"
    private var nextId = 0L
    private var users = emptyList<User>()
    private val data = MutableLiveData(users)

    init {
        prefs.getString(userKey, null)?.let {
            users = gson.fromJson(it, type)
            nextId = users.maxByOrNull { it.id }?.id?.plus(1) ?: 1L
            data.value = users
        }
    }

    override fun getAllUsers(): LiveData<List<User>> = data
  /*  override fun getUser(): LiveData<User> {
        val userJson = prefs.getString(userKey, null)
        val userProfile = if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            User(0,"","","",1,1)
        }
        val data = MutableLiveData<User>()
        data.value = userProfile
        return data
    }*/

    override fun saveUser(user: User) {
        if (user.id == 0L) {
            users = listOf(
                user.copy(
                    id = nextId++,
                    registrationDate = "now"

                )
            ) + users
            data.value = users
            syncUsers()
            return
        }
        data.value = users
        syncUsers()

    }

    override fun saveUserWithAvatar(user: User, upload: MediaUpload) {
        //val media = upload(upload)
        val userWithAvatar = user.copy(avatar = Avatar(/*media.id,*/ AttachmentType.IMAGE))
        saveUser(userWithAvatar)
    }

    override fun UserExist(username: String, password: String): Boolean {
        val existingUser = users.find { it.nickName == username && it.password == password }
        return existingUser != null
    }

    private fun syncUsers() {
        with(prefs.edit()) {
            putString(userKey, gson.toJson(users))
            apply()
        }
    }
}