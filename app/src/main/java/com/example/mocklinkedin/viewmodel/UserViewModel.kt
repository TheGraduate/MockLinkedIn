package com.example.mocklinkedin.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mocklinkedin.db.AppDb
import com.example.mocklinkedin.dto.User
import com.example.mocklinkedin.model.FeedModel
import com.example.mocklinkedin.model.FeedModelState
import com.example.mocklinkedin.model.PhotoModel
import com.example.mocklinkedin.repository.UserRepository
import com.example.mocklinkedin.repository.UserRepositoryImpl
import com.example.mocklinkedin.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File

private val empty = User(
    id = 0,
    login = "",
    name = "",
    //password = "",
    //registrationDate = 0,
    avatar = null
)

private val noPhoto = PhotoModel()

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository =
        UserRepositoryImpl(AppDb.getInstance(context = application).userDao())

    val data: LiveData<FeedModel<User>> = repository.data
        .map(::FeedModel)
        .asLiveData(Dispatchers.Default)

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

   /* val newerCount: LiveData<Int> = data.switchMap {
        repository.getNewerCount(it.objects.firstOrNull()?.id ?: 0L)
            .catch { e -> e.printStackTrace() }
            .asLiveData(Dispatchers.Default)
    }*/

    val edited = MutableLiveData(empty)
    private val _userCreated = SingleLiveEvent<Unit>()
    val userCreated: LiveData<Unit>
        get() = _userCreated

    private val _userAuthenticated = MutableLiveData<Boolean>()
    val userAuthenticated: LiveData<Boolean>
        get() = _userAuthenticated


    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<PhotoModel>
        get() = _photo

    init {
        loadUsers()
    }

     fun loadUsers() {
         viewModelScope.launch {
             try {
                 _dataState.value = FeedModelState(loading = true)
                 repository.getAllUsers()
                 _dataState.value = FeedModelState()
             } catch (e: Exception) {
                 _dataState.value = FeedModelState(error = true)
             }
         }
     }

    fun changePhoto(uri: Uri?, file: File?) {
        _photo.value = PhotoModel(uri, file)
    }
    // fun getUser(): LiveData<User>
    /* fun saveUser(name: String, firstName: String, password: String) {
        edited.value?.let {
            _userCreated.value = Unit
            viewModelScope.launch {
                try {
                    when(_photo.value) {
                        noPhoto -> repository.saveUser(name,firstName,password)
                        else -> _photo.value?.file?.let { file ->
                            repository.registrationUser(name, firstName, password, MediaUpload(file))
                        }
                    }

                    _dataState.value = FeedModelState()
                } catch (e: Exception) {
                    _dataState.value = FeedModelState(error = true)
                }
            }
        }
        edited.value = empty
        _photo.value = noPhoto
     }*/

    /*fun authenticateUser(name: String, password: String): Boolean {
        var isAuthenticated = false
        viewModelScope.launch {
            try {
                _dataState.value = FeedModelState(loading = true)
                isAuthenticated = repository.authUser(name,password)
                _userAuthenticated.value = repository.authUser(name,password)
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
        return isAuthenticated
    }*/
}