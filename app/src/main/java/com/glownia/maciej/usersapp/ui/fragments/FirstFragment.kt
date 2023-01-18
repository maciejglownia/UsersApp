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
import androidx.recyclerview.widget.LinearLayoutManager
import com.glownia.maciej.usersapp.adapters.UsersAdapter
import com.glownia.maciej.usersapp.databinding.FragmentFirstBinding
import com.glownia.maciej.usersapp.ui.viewmodels.MainViewModel
import com.glownia.maciej.usersapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Displays list with users
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val myAdapter by lazy { UsersAdapter() }

    override fun onResume() {
        super.onResume()
        if (mainViewModel.recyclerViewState != null) {
            binding.recyclerView.layoutManager?.onRestoreInstanceState(mainViewModel.recyclerViewState)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            readDatabase()
        }
        return binding.root
    }
    private fun setupRecyclerView() {
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // TODO: Show loading
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readUsersGithub.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("FirstFragment", "readDatabase called!")
                    myAdapter.setData(database.first().usersGithub)
                    // TODO: Hide loading
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        Log.d("FirstFragment", "requestApiData called!")
        mainViewModel.getUsersFromApis()
        mainViewModel.usersGithubResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    // TODO: Hide loading
                    response.data?.let { myAdapter }
                }
                is NetworkResult.Error -> {
                    Log.d("FirstFragment", "requestApiData error!")
                    // TODO: Hide loading
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("FirstFragment", "requestApiData loading...")
                    // TODO: Show loading
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readUsersGithub.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    myAdapter.setData(database.first().usersGithub)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.recyclerViewState =
            binding.recyclerView.layoutManager?.onSaveInstanceState()
        _binding = null
    }
}