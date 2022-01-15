/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryInfoBinding
import jp.co.yumemi.android.code_check.viewModels.RepositoryInfoViewModel

@AndroidEntryPoint
class RepositoryInfoFragment : Fragment(R.layout.fragment_repository_info) {
    private val args: RepositoryInfoFragmentArgs by navArgs()
    private val viewModel: RepositoryInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentRepositoryInfoBinding.bind(view).apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        viewModel.init(args.repositoryInfo)
    }
}
