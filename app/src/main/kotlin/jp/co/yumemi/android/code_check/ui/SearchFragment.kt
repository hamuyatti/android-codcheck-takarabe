/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.viewModels.Repository
import jp.co.yumemi.android.code_check.viewModels.SearchViewModel
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        logLastSearchDate()
        val adapter = initializeAdapter()
        setUpRecyclerView(adapter)
        observe(adapter)
        setUpInputTextLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeAdapter(): RepositoryListAdapter {
        return RepositoryListAdapter(object : RepositoryListAdapter.OnItemClickListener {
            override fun itemClick(repository: Repository) {
                gotoRepositoryFragment(repository)
            }
        })
    }

    private fun setUpRecyclerView(adapter: RepositoryListAdapter) {
        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    private fun setUpInputTextLayout(){
        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == KeyEvent.ACTION_DOWN) return@setOnEditorActionListener false

                if (editText.length() == 0) {
                    showDialog(getString(R.string.no_input_text))
                } else if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        viewModel.searchResults(it)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.searchInputLayout.setEndIconOnClickListener {
            binding.searchInputText.setText("")
        }
    }

    private fun observe(adapter: RepositoryListAdapter){
        viewModel.errorLD.observe(viewLifecycleOwner) {
            if (it) {
                showDialog(getString(R.string.if_error_when_search))
            }
        }
        viewModel.repositoryList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    fun gotoRepositoryFragment(item: Repository) {
        val action = SearchFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(repository = item)
        findNavController().navigate(action)
    }

    private fun logLastSearchDate() {
        viewModel.lastSearchDate.observe(viewLifecycleOwner) {
            Timber.tag(getString(R.string.searching_time)).d("$it")
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