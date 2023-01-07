package com.glownia.maciej.usersapp.ui.viewmodels

import android.app.Application
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.*
import com.glownia.maciej.usersapp.data.Repository
import com.glownia.maciej.usersapp.data.database.entities.UsersEntity
import com.glownia.maciej.usersapp.models.UsersGithub
import com.glownia.maciej.usersapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application,
) : AndroidViewModel(application) {

    var recyclerViewState: Parcelable? = null

    /** Room Database */
    val readUsersGithub: LiveData<List<UsersEntity>> = repository.local.readUsersGithub().asLiveData()

    private fun insertUsersGithub(usersEntity: UsersEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUsersGithub(usersEntity)
        }

    /** Retrofit */
    var usersGithubResponse: MutableLiveData<NetworkResult<UsersGithub>> = MutableLiveData()

    // TODO:  response user daily
    fun getUsersGithub() = viewModelScope.launch {
        getUsersGithubSafeCall()
    }

    private suspend fun getUsersGithubSafeCall() {
        usersGithubResponse.value = NetworkResult.Loading()
        try {
            val response = repository.remote.getUsersGithub()
            Log.i("MainViewModel", "getUsersSafeCall: getUsersGithub()")
            usersGithubResponse.value = handleUsersGithubResponse(response)
            val usersGithub = usersGithubResponse.value!!.data
            if (usersGithub != null) {
                offlineCacheUsersGithub(usersGithub)
                Log.i("MainViewModel", "getUsersGithubSafeCall: offlineCacheUsersGithub()")
            }
        } catch (e: Exception) {
            usersGithubResponse.value = NetworkResult.Error("Users not found.")
            Log.i("MainViewModel", "getUsersGithubSafeCall: Exception")

        } catch (e: HttpException) {
            usersGithubResponse.value = NetworkResult.Error("No Internet connection")
            Log.i("MainViewModel", "getUsersGithubSafeCall: Http exception")
        }
    }

    private fun offlineCacheUsersGithub(usersGithub: UsersGithub) {
        val usersGithubEntity = UsersEntity(usersGithub)
        insertUsersGithub(usersGithubEntity)
    }

    private fun handleUsersGithubResponse(response: Response<UsersGithub>): NetworkResult<UsersGithub> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.body()!!.resultsUsersGithub.isEmpty() -> {
                NetworkResult.Error("Users not found.")
            }
            response.isSuccessful -> {
                val usersGithub = response.body()
                NetworkResult.Success(usersGithub!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }
}