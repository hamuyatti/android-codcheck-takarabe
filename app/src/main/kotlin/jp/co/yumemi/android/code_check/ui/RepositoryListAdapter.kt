package jp.co.yumemi.android.code_check.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutItemBinding
import jp.co.yumemi.android.code_check.entity.RepositoryInfo

class RepositoryListAdapter(
    private val itemClickListener: OnItemClickListener
) : ListAdapter<RepositoryInfo, ItemViewHolder>(ResultDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent, itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    interface OnItemClickListener {
        fun itemClick(repositoryInfo: RepositoryInfo)
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

    fun bind(item: RepositoryInfo) {
        binding.repositoryInfo = item
        binding.onClickLister = clickListener
    }
}


class ResultDiffCallBack : DiffUtil.ItemCallback<RepositoryInfo>() {
    override fun areItemsTheSame(oldItem: RepositoryInfo, newItem: RepositoryInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepositoryInfo, newItem: RepositoryInfo): Boolean {
        return oldItem == newItem
    }

}