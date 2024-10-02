package com.example.getirlite.view.sheet.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.getirlite.databinding.SheetHelpBinding
import com.example.getirlite.view.sheet.BaseSheet
import com.example.getirlite.view.sheet.help.components.FAQSectionAdapter

class HelpSheet: BaseSheet() {
    private var binding: SheetHelpBinding? = null
    private var adapter: FAQSectionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFullScreen = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SheetHelpBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        val binding = binding ?: return
        adapter = FAQSectionAdapter()
        binding.recyclerFaqSection.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.recyclerFaqSection?.adapter = null
        adapter = null
        binding = null
    }

    companion object {
        const val TAG = "HelpSheet"
    }
}