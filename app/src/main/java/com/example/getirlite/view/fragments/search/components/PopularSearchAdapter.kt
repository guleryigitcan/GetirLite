package com.example.getirlite.view.fragments.search.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getirlite.databinding.CellSearchWordsBinding
import com.example.getirlite.model.extension.title
import com.example.getirlite.view.fragments.search.PopularSearchKeywords

class PopularSearchAdapter(
    private var keywords: List<PopularSearchKeywords> = PopularSearchKeywords.entries.shuffled().take(10),
    private val onSearchSelected: (String) -> Unit
): RecyclerView.Adapter<PopularSearchAdapter.PopularSearchViewHolder>() {

    inner class PopularSearchViewHolder(private val binding: CellSearchWordsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(keyword: PopularSearchKeywords) {
            binding.label.text = keyword.name
            binding.main.setOnClickListener { onSearchSelected(keyword.name) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularSearchViewHolder =
        PopularSearchViewHolder(
            CellSearchWordsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PopularSearchViewHolder, position: Int) = holder.setData(keyword = keywords[position])

    override fun getItemCount(): Int = keywords.size
}