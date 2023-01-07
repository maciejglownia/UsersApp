package com.glownia.maciej.usersapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.glownia.maciej.usersapp.databinding.FragmentSecondBinding

/**
 * Displays single user
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<SecondFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        setupDetailsRow()

        return binding.root

    }

    // Sets data to display using arguments passed from item has been clicked in the Recycler View
    private fun setupDetailsRow() {
        binding.apply {
            avatarImageView.load(args.user.avatar)
            usernameTextView.text = args.user.username
            urlTextView.text = args.user.url
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}