package com.glownia.maciej.usersapp.ui.viewmodels.usergithub

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glownia.maciej.usersapp.data.Repository
import com.glownia.maciej.usersapp.data.database.entities.UserGithubDetailsEntity
import com.glownia.maciej.usersapp.models.usergithubdetails.UserGithubDetails
import com.glownia.maciej.usersapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserGithubViewModel @Inject constructor(
    private val repository: Repository,
    application: Application,
) : AndroidViewModel(application) {


    /** Room Database */
    private fun insertUserGithubDetailsOfChosenUser(userGithubDetailsEntity: UserGithubDetailsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUserGithubDetailsOfChosenUser(userGithubDetailsEntity)
        }

    /** Retrofit */
    private val userGithubDetailsResponse: MutableLiveData<NetworkResult<UserGithubDetails>> =
        MutableLiveData()

    fun getUserGithubDetailsFromApi(login: String) {
        getUserGithubDetailsFromApiSafeCall(login)
    }

    private fun getUserGithubDetailsFromApiSafeCall(login: String) = viewModelScope.launch {
        userGithubDetailsResponse.value = NetworkResult.Loading()
        try {
            runBlocking {
                Log.i("UserGithubViewModel",
                    "getUserDetailsFromApiSafeCall is getting user details...")
                val response = repository.remote.getUserGithubDetails(login)
                userGithubDetailsResponse.value = handleUserGithubDetailsResponse(response)

                val userGithubDetails = userGithubDetailsResponse.value!!.data
                userGithubDetails?.let { offlineCacheUserGithubDetails(it) }
            }
        } catch (e: Exception) {
            userGithubDetailsResponse.value = NetworkResult.Error("User details not found.")
            Log.i("UserGithubViewModel", "getUserDetailsFromApiSafeCall: Exception")

        } catch (e: HttpException) {
            userGithubDetailsResponse.value = NetworkResult.Error("No Internet connection")
            Log.i("UserGithubViewModel", "getUserDetailsFromApiSafeCall: Http exception")
        }
    }

    /**
     * Handles response from API and returns [NetworkResult].
     */
    private fun handleUserGithubDetailsResponse(response: Response<UserGithubDetails>): NetworkResult<UserGithubDetails> {
        when {
            response.isSuccessful -> {
                val userGithubDetailsResponse = response.body()
                return NetworkResult.Success(userGithubDetailsResponse!!)
            }
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.body()!!.toString().isEmpty() -> {
                return NetworkResult.Error("Github user's details not found.")
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    /**
     * Saves book details into [UsersDatabase]
     * Table contains user details of book
     * which has been chosen (clicked) on main screen with user list.
     * Entity: [UserGithubDetailsEntity]
     */
    private fun offlineCacheUserGithubDetails(userGithubDetails: UserGithubDetails) {
            val userGithubDetailsEntity = UserGithubDetailsEntity(
                id = userGithubDetails.id,
                login = userGithubDetails.login,
                name = userGithubDetails.name,
                location = userGithubDetails.location,
                avatarUrl = userGithubDetails.avatarUrl,
                blog = userGithubDetails.blog,
                company = userGithubDetails.company,
                createdAt = userGithubDetails.createdAt,
                type = userGithubDetails.type,
                url = userGithubDetails.url,
            )
            Log.i("BookDetailsViewModel", "Saving book details into database...")
            insertUserGithubDetailsOfChosenUser(userGithubDetailsEntity)
            Log.i("BookDetailsViewModel", "Book details has been saved!")
    }
}
