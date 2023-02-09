package com.glownia.maciej.usersapp.ui.viewmodels

import android.app.Application
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.*
import com.glownia.maciej.usersapp.data.Repository
import com.glownia.maciej.usersapp.data.database.entities.UsersDailymotionEntity
import com.glownia.maciej.usersapp.data.database.entities.UsersGithubEntity
import com.glownia.maciej.usersapp.models.ResultGithub
import com.glownia.maciej.usersapp.models.UsersDailymotion
import com.glownia.maciej.usersapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    val readUsersGithub: LiveData<List<UsersGithubEntity>> =
        repository.local.readUsersGithub().asLiveData()
    val readUsersDailymotion: LiveData<List<UsersDailymotionEntity>> =
        repository.local.readUsersDailymotion().asLiveData()

    private fun insertUsersGithub(usersGithubEntity: UsersGithubEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUsersGithub(usersGithubEntity)
        }

    private fun insertUsersDailymotion(usersDailymotionEntity: UsersDailymotionEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUsersDailymotion(usersDailymotionEntity)
        }

    /** Retrofit */
    var usersGithubResponse: MutableLiveData<NetworkResult<List<ResultGithub>>> = MutableLiveData()
    private var usersDailymotionResponse: MutableLiveData<NetworkResult<UsersDailymotion>> =
        MutableLiveData()

    fun getUsersFromApis() {
        getUsersFromApisSafeCall()
    }

    private fun getUsersFromApisSafeCall() = viewModelScope.launch {
        usersGithubResponse.value = NetworkResult.Loading()
        try {
            val responseUsersGithub = async { repository.remote.getUsersGithub() }
            val responseUsersDailymotion = async { repository.remote.getUsersDailymotion() }

            Log.i("MainViewModel", "getUsersFromApisSafeCall is trying...")

            val github = responseUsersGithub.await()
            val dailymotion = responseUsersDailymotion.await()

            handleDataResponses(github, dailymotion)
            saveDataFromApiIntoDatabase()
        } catch (e: Exception) {
            usersGithubResponse.value = NetworkResult.Error("Users not found.")
            Log.i("MainViewModel", "getUsersFromApisSafeCall: Exception")

        } catch (e: HttpException) {
            usersGithubResponse.value = NetworkResult.Error("No Internet connection")
            Log.i("MainViewModel", "getUsersFromApisSafeCall: Http exception")
        }
    }

    private fun handleDataResponses(
        github: Response<List<ResultGithub>>,
        dailymotion: Response<UsersDailymotion>,
    ) {
        usersGithubResponse.value = handleUsersGithubResponse(github)
        usersDailymotionResponse.value = handleUsersDailymotionResponse(dailymotion)
    }

    private fun saveDataFromApiIntoDatabase() {
        Log.i("MainViewModel", "saving data from API into database...")
        offlineCacheUsersGithub()
        offlineCacheUsersDailymotion()
    }

    private fun offlineCacheUsersGithub() {
        usersGithubResponse.value!!.data?.forEach { user ->
            val usersGithubEntity = UsersGithubEntity(
                avatarUrl = user.avatarUrl,
                id = user.id,
                login = user.login,
                type = user.type,
                url = user.url,
            )
            insertUsersGithub(usersGithubEntity)
        }
    }

    private fun offlineCacheUsersDailymotion() {
        val usersDailymotion = usersDailymotionResponse.value!!.data
        usersDailymotion?.resultsUserDailymotion?.forEach { user ->
            val usersDailymotionEntity =
                UsersDailymotionEntity(
                    id = user.id,
                    screenName = user.screenName
                )
            insertUsersDailymotion(usersDailymotionEntity)
        }
    }

    private fun handleUsersGithubResponse(responseUsersGithub: Response<List<ResultGithub>>): NetworkResult<List<ResultGithub>> {
        return when {
            responseUsersGithub.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            responseUsersGithub.body()!!.isEmpty() -> {
                NetworkResult.Error("Github users not found.")
            }
            responseUsersGithub.isSuccessful -> {
                val usersGithub = responseUsersGithub.body()
                NetworkResult.Success(usersGithub!!)
            }
            else -> {
                NetworkResult.Error(responseUsersGithub.message())
            }
        }
    }

    private fun handleUsersDailymotionResponse(responseUsersDailymotion: Response<UsersDailymotion>): NetworkResult<UsersDailymotion> {
        return when {
            responseUsersDailymotion.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            responseUsersDailymotion.body()!!.resultsUserDailymotion.isEmpty() -> {
                NetworkResult.Error("Dailymotion users not found.")
            }
            responseUsersDailymotion.isSuccessful -> {
                val usersDailymotion = responseUsersDailymotion.body()
                NetworkResult.Success(usersDailymotion!!)
            }
            else -> {
                NetworkResult.Error(responseUsersDailymotion.message())
            }
        }
    }
}