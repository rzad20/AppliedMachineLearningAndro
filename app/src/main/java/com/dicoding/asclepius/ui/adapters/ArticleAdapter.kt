package com.dicoding.asclepius.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.ArticlesItemBinding

class ArticleAdapter : ListAdapter<ArticlesItem, ArticleAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private val listArticle = ArrayList<ArticlesItem>()
    fun setData(listArticle : List<ArticlesItem>) {
        this.listArticle.clear()
        this.listArticle.addAll(listArticle)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ArticlesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articleItem = listArticle[position]
        holder.bind(articleItem)
    }

    override fun getItemCount(): Int = listArticle.size
    class MyViewHolder(private val binding: ArticlesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem) {
            binding.articleTitle.text = article.title
            binding.articleAuthor.text = article.author
            binding.articleDescription.text = article.description
            Glide.with(binding.articleImage.context)
                .load(article.urlToImage)
                .into(binding.articleImage)
        }
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
                oldItem == newItem
        }
    }
}