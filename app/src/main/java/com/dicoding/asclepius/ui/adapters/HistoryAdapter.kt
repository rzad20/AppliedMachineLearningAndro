package com.dicoding.asclepius.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.local.entity.AnalyzeHistory
import com.dicoding.asclepius.databinding.HistoryItemCardBinding

class HistoryAdapter : ListAdapter<AnalyzeHistory, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HistoryItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val historyItem = getItem(position)
        holder.bind(historyItem)
    }
    class MyViewHolder(private val binding: HistoryItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history : AnalyzeHistory) {
            binding.predictedLabelView.text = history.predictedLabel
            binding.confidenceScoreView.text = "Confidence Score : " + history.confidenceScore
            Glide.with(binding.userImage.context)
                .load(history.imageUri)
                .into(binding.userImage)
        }
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnalyzeHistory>() {
            override fun areItemsTheSame(oldItem: AnalyzeHistory, newItem: AnalyzeHistory): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AnalyzeHistory, newItem: AnalyzeHistory): Boolean =
                oldItem == newItem
        }
    }
}