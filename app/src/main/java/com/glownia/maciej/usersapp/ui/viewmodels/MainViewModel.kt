package com.glownia.maciej.usersapp.ui.viewmodels

import android.app.Application
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.*
import com.glownia.maciej.usersapp.data.Repository
import com.glownia.maciej.usersapp.data.database.entities.UsersEntity
import com.glownia.maciej.usersapp.models.UsersDailymotion
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
    var usersDailymotionResponse: MutableLiveData<NetworkResult<UsersDailymotion>> = MutableLiveData()

    fun getUsersGithubDailymotion() = viewModelScope.launch {
        getUsersGithubDailymotionSafeCall()
    }

    private suspend fun getUsersGithubDailymotionSafeCall() {
        usersGithubResponse.value = NetworkResult.Loading()
        try {
            val responseUsersGithub = repository.remote.getUsersGithub()
            val responseUsersDailymotion = repository.remote.getUsersDailymotion()
            Log.i("MainViewModel", "getUsersSafeCall: getUsersGithub()")
            usersGithubResponse.value = handleUsersGithubResponse(responseUsersGithub)
            usersDailymotionResponse.value = handleUsersDailymotionResponse(responseUsersDailymotion)
            val usersGithub = usersGithubResponse.value!!.data
            val usersDailymotion = usersDailymotionResponse.value!!.data
            if (usersGithub != null) {
                if (usersDailymotion != null) {
                    offlineCacheUsers(usersGithub, usersDailymotion)
                }
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


    private fun offlineCacheUsers(usersGithub: UsersGithub, usersDailymotion: UsersDailymotion) {
        val usersEntity = UsersEntity(usersGithub, usersDailymotion)
        insertUsersGithub(usersEntity)
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
    private fun handleUsersDailymotionResponse(responseUsersDailymotion: Response<UsersDailymotion>): NetworkResult<UsersDailymotion>? {
        return when {
            responseUsersDailymotion.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            responseUsersDailymotion.body()!!.results.isEmpty() -> {
                NetworkResult.Error("Users not found.")
            }
            responseUsersDailymotion.isSuccessful -> {
                val usersGithub = responseUsersDailymotion.body()
                NetworkResult.Success(usersGithub!!)
            }
            else -> {
                NetworkResult.Error(responseUsersDailymotion.message())
            }
        }
    }
}