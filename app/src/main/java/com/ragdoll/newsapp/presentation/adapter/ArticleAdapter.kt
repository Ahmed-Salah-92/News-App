package com.ragdoll.newsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ragdoll.newsapp.R
import com.ragdoll.newsapp.data.model.Article
import com.ragdoll.newsapp.databinding.ArticleListItemBinding
import com.ragdoll.newsapp.presentation.adapter.ArticleAdapter.ArticleVH

class ArticleAdapter : RecyclerView.Adapter<ArticleVH>() {

    private var onItemClickListener: ((Article) -> Unit)? = null

    // Set the listener for item clicks
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    inner class ArticleVH(val binding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.articleTv.text = article.title
            Glide
                .with(binding.articleImv.context)
                .load(article.urlToImage)
                .error(R.drawable.ic_broken_image)
                .into(binding.articleImv)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }

    val diffUtil = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        val binding = ArticleListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleVH(binding)
    }

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        val article = diffUtil.currentList[position]
        holder.bind(article)

        /*holder.binding.articleTv.text = articles[position].title
        Glide
            .with(holder.binding.articleImv.context)
            .load(articles[position].urlToImage)
            .error(R.drawable.ic_broken_image)
            .into(holder.binding.articleImv)
        holder.binding.root.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, articles[position].url.toUri())
            activity.startActivity(intent)
        }*/
        //Log.d("trace", "Image: ${articles[position].urlToImage}")
    }

    override fun getItemCount(): Int = diffUtil.currentList.size

}