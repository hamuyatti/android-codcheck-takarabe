package jp.co.yumemi.android.code_check.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.viewModels.Repository
import jp.co.yumemi.android.code_check.databinding.LayoutItemBinding

class RepositoryListAdapter(
    private val itemClickListener: OnItemClickListener
) : ListAdapter<Repository, ItemViewHolder>(ResultDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent, itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    interface OnItemClickListener {
        fun itemClick(repository: Repository)
    }
}

class ItemViewHolder(
    private val binding: LayoutItemBinding,
    private val clickListener: RepositoryListAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(
            parent: ViewGroup,
            clickListener: RepositoryListAdapter.OnItemClickListener
        ): ItemViewHolder {
            return ItemViewHolder(
                LayoutItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), clickListener
            )
        }
    }

    fun bind(item: Repository) {
        binding.repositoryNameView.text = item.name
        binding.root.setOnClickListener {
            clickListener.itemClick(item)
        }
    }
}


class ResultDiffCallBack : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

}