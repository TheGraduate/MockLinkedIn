package com.example.mocklinkedin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mocklinkedin.dto.MediaUpload
import com.example.mocklinkedin.dto.User
import com.example.mocklinkedin.model.PhotoModel
import com.example.mocklinkedin.repository.PostRepository
import com.example.mocklinkedin.repository.PostRepositorySharedPrefsImpl
import com.example.mocklinkedin.repository.UserRepository
import com.example.mocklinkedin.repository.UserRepositorySharedPrefsImpl

private val empty = User(
    id = 0,
    nickName = "",
    firstName = "",
    lastName = "",
    password = "",
    birthDate = 0,
    registrationDate = "now",
    avatar = null
)

private val noPhoto = PhotoModel()

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = UserRepositorySharedPrefsImpl(application)
    val data = repository.getAllUsers()
    val edited = MutableLiveData(empty)
    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<PhotoModel>
        get() = _photo

    fun setUserData(username: String, firstName: String, lastName: String, password: String) {
        val newUser = User(
            id = 0, // Значение id будет установлено в репозитории
            nickName = username,
            firstName = firstName,
            lastName = lastName,
            password = password,
            birthDate = 0, // или установите значение по вашему усмотрению
            registrationDate = "now",
            avatar = null // или установите значение по вашему усмотрению
        )

        edited.value = newUser
    }

    fun UserExist(username: String, password: String): Boolean {
        return repository.UserExist(username, password)
    }

    fun saveUser() {
        edited.value?.let {
            //repository.save(it)
            when(_photo.value) {
                noPhoto -> repository.saveUser(it)
                else -> _photo.value?.file?.let { file ->
                    repository.saveUserWithAvatar(it, MediaUpload(file))
                }
            }
        }
        edited.value = empty
    }

   /* fun changeContent(nickName: String, firstName: String, lastName: String, password: String) {
        //val text = content .trim()
        val text = nickName.trim()
        if (edited.value?.nickName = text
            ) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }*/
}