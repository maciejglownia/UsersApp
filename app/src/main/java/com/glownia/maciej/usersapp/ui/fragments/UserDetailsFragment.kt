package com.glownia.maciej.usersapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.glownia.maciej.usersapp.databinding.FragmentUserDetailsBinding
import com.glownia.maciej.usersapp.ui.viewmodels.usergithub.UserGithubViewModel

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
        userGithubViewModel.getUserGithubDetailsFromApi(args.user.login.toString())

        setupDetailsRow()

        return binding.root

    }

    // Sets data to display using arguments passed from item has been clicked in the Recycler View
    private fun setupDetailsRow() {
        binding.apply {
            avatarImageView.load(args.user.avatarUrl)
            usernameTextView.text = args.user.login
            urlTextView.text = args.user.url
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}