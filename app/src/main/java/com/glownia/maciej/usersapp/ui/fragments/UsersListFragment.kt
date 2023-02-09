package com.glownia.maciej.usersapp.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.glownia.maciej.usersapp.adapters.UsersAdapter
import com.glownia.maciej.usersapp.databinding.FragmentUsersListBinding
import com.glownia.maciej.usersapp.ui.viewmodels.MainViewModel
import com.glownia.maciej.usersapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Displays list with users
 */
@AndroidEntryPoint
class UsersListFragment : Fragment() {

    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { UsersAdapter() }

    override fun onResume() {
        super.onResume()
        if (viewModel.recyclerViewState != null) {
            binding.recyclerView.layoutManager?.onRestoreInstanceState(viewModel.recyclerViewState)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            readDatabase()
        }

        myAdapter.setOnItemClickListener {
            val action = UsersListFragmentDirections
                .actionUsersListFragmentToUserDetailsFragment(it)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = myAdapter
        // Display 2 columns when the app orientation is setup to portrait and 5 when setup to landscape
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        }
        // TODO: Show loading
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            Log.d("UsersListFragment", "readDatabase called!")
            viewModel.readUsersGithub.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    myAdapter.setData(database)
                    // TODO: Hide loading
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        Log.d("UsersListFragment", "requestApiData called!")
        viewModel.getUsersFromApis()
        viewModel.usersGithubResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.d("UsersListFragment", "network result successful")
                    // TODO: Hide loading
                }
                is NetworkResult.Error -> {
                    Log.d("UsersListFragment", "requestApiData error!")
                    // TODO: Hide loading
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("UsersListFragment", "requestApiData loading...")
                    // TODO: Show loading
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.recyclerViewState =
            binding.recyclerView.layoutManager?.onSaveInstanceState()
        _binding = null
    }
}