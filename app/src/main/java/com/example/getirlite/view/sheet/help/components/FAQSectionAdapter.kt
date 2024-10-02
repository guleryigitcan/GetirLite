package com.example.getirlite.view.sheet.help.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getirlite.databinding.CellFaqSectionBinding
import com.example.getirlite.view.sheet.help.model.FAQSection


class FAQSectionAdapter(private val sections: List<FAQSection> = FAQSection.entries): RecyclerView.Adapter<FAQSectionAdapter.FAQSectionViewHolder>() {

    inner class FAQSectionViewHolder(private val binding: CellFaqSectionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(section: FAQSection) {
            binding.title.text = section.title
            binding.recyclerFaqItems.adapter = FAQItemAdapter(section.items)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQSectionViewHolder = FAQSectionViewHolder(
        CellFaqSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: FAQSectionViewHolder, position: Int) = holder.setData(section = sections[position])

    override fun getItemCount(): Int = sections.size
}