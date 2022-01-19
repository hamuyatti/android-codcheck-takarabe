/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.viewModels.SearchViewModel
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding
import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import jp.co.yumemi.android.code_check.entity.Resource
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        val adapter = initializeAdapter()
        setUpRecyclerView(adapter)
        collect(adapter)
        setUpInputTextLayout()
        logLastSearchDate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeAdapter(): RepositoryListAdapter {
        return RepositoryListAdapter(object : RepositoryListAdapter.OnItemClickListener {
            override fun itemClick(repositoryInfo: RepositoryInfo) {
                navigateRepositoryInfoFragment(repositoryInfo)
            }
        })
    }

    private fun setUpRecyclerView(adapter: RepositoryListAdapter) {
        val layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                (binding.recyclerView.layoutManager as GridLayoutManager).scrollToPositionWithOffset(
                    positionStart,
                    0
                )
            }
        })
    }

    private fun setUpInputTextLayout() {
        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == KeyEvent.ACTION_DOWN) return@setOnEditorActionListener false

                if (editText.length() == 0) {
                    showDialog(getString(R.string.no_input_text))
                } else if (action == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchResults(editText.text.toString())
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.searchInputLayout.setEndIconOnClickListener {
            binding.searchInputText.setText("")
        }
    }

    private fun collect(adapter: RepositoryListAdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    viewModel.setVisible(false)
                    when (it) {
                        is Resource.Success -> adapter.submitList(it.data)
                        is Resource.Failed -> handleError(it.throwable)
                        is Resource.Empty -> {}
                    }
                }
            }
        }
    }

    fun navigateRepositoryInfoFragment(item: RepositoryInfo) {
        val action = SearchFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(repositoryInfo = item)
        findNavController().navigate(action)
    }

    private fun logLastSearchDate() {
        viewModel.lastSearchDate.observe(viewLifecycleOwner) {
            Timber.tag(getString(R.string.searching_time)).d("$it")
        }
    }

    private fun handleError(throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                showDialog(getString(R.string.if_io_exception))
            }
            else -> {
                showDialog(getString(R.string.if_error_when_search))
            }
        }
    }

    private fun showDialog(message: String) {
        CustomDialog.showDialog(
            "",
            message,
            childFragmentManager,
            CustomDialog::class.simpleName ?: return
        )
    }
}
