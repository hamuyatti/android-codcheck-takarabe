/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryInfoBinding

@AndroidEntryPoint
class RepositoryInfoFragment : Fragment(R.layout.fragment_repository_info) {

    private val args: RepositoryInfoFragmentArgs by navArgs()
    private var _binding: FragmentRepositoryInfoBinding? = null
    private val binding: FragmentRepositoryInfoBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRepositoryInfoBinding.bind(view)
        var item = args.item
        binding.ownerIconView.load(item.ownerIconUrl);
        binding.nameView.text = item.name;
        binding.languageView.text = item.language;
        binding.starsView.text = "${item.stargazersCount} stars";
        binding.watchersView.text = "${item.watchersCount} watchers";
        binding.forksView.text = "${item.forksCount} forks";
        binding.openIssuesView.text = "${item.openIssuesCount} open issues";
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
