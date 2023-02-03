package com.glownia.maciej.usersapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.glownia.maciej.usersapp.databinding.FragmentUserDetailsBinding
import com.glownia.maciej.usersapp.ui.viewmodels.usergithub.UserGithubViewModel
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
        lifecycleScope.launchWhenStarted {
            readDatabase()
        }

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
                        Log.d("UserDetailsFragment", "readDatabase called!")
                        binding.avatarImageView.load(database.avatarUrl)
                        binding.usernameTextView.text = database.name.toString()
                        binding.urlTextView.text = database.url.toString()
                    } else {
                        Log.d("UserDetailsFragment", "API call needed!")
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}