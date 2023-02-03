package com.glownia.maciej.usersapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.glownia.maciej.usersapp.data.database.entities.UserGithubDetailsEntity
import com.glownia.maciej.usersapp.databinding.FragmentUserDetailsBinding
import com.glownia.maciej.usersapp.ui.viewmodels.usergithub.UserGithubViewModel
import com.glownia.maciej.usersapp.utils.NetworkResult
import kotlinx.coroutines.launch

/**
 * Displays single user
 */
class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var userGithubViewModel: UserGithubViewModel

    private val args by navArgs<UserDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userGithubViewModel = ViewModelProvider(requireActivity())[UserGithubViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        readDatabase()

        return binding.root
    }

    /**
     * Reads data from database if it is there. In other way call API to get a new data.
     */
    private fun readDatabase() {
        lifecycleScope.launch {
            userGithubViewModel.readUserGithubByLogin(args.user.login)
                .observe(viewLifecycleOwner) { database ->
                    if (database != null && database.login == args.user.login) {
                        Log.d("UserDetailsFragment", "readDatabase() called!")
                        setupLayoutFromDatabase(database)
                    } else {
                        requestApiData()
                    }
                }
        }
    }

    /**
     * Setups layout based on data retrieved from database.
     */
    private fun setupLayoutFromDatabase(database: UserGithubDetailsEntity) {
        binding.avatarImageView.load(database.avatarUrl)
        binding.usernameTextView.text = database.name.toString()
        binding.urlTextView.text = database.url.toString()
    }

    /**
     * When database does not contain proper user, it calls API to retrieve one.
     * After API call user is saving into database [UserGithubViewModel].
     * Then data is available to display.
     */
    private fun requestApiData() {
        Log.d("UserDetailsFragment", "requestApiData() called!")
        userGithubViewModel.getUserGithubDetailsFromApi(args.user.login)
        userGithubViewModel.userGithubDetailsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    // TODO: Hide loading
                    Log.d("UserDetailsFragment", "Network success. Hiding loading image.")
                }
                is NetworkResult.Error -> {
                    Log.d("UserDetailsFragment", "requestApiData error!")
                    // TODO: Hide loading
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("UserDetailsFragment", "requestApiData loading...")
                    // TODO: Show loading
                }
            }
        }
    }

    /**
     * Reads data from database in case of network error.
     */
    private fun loadDataFromCache() {
        lifecycleScope.launch {
            userGithubViewModel.readUserGithubByLogin(args.user.login)
                .observe(viewLifecycleOwner) { database ->
                    if (database != null && database.login == args.user.login) {
                        Log.d("UserDetailsFragment", "loadDataFromCache() called.")
                        setupLayoutFromDatabase(database)

                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}