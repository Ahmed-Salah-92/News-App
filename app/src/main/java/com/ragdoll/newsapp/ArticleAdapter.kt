package com.ragdoll.newsapp

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ragdoll.newsapp.ArticleAdapter.ArticleVH
import com.ragdoll.newsapp.databinding.ArticleListItemBinding

class ArticleAdapter(private val activity: Activity, private val articles: List<Article>) :
    RecyclerView.Adapter<ArticleVH>() {

    class ArticleVH(val binding: ArticleListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        val binding = ArticleListItemBinding.inflate(
            activity.layoutInflater,
            parent,
            false
        )
        return ArticleVH(binding)
    }

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        holder.binding.articleTv.text = articles[position].title
        Glide
            .with(holder.binding.articleImv.context)
            .load(articles[position].urlToImage)
            .error(R.drawable.ic_broken_image)
            .into(holder.binding.articleImv)
        holder.binding.root.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, articles[position].url.toUri())
            activity.startActivity(intent)
        }
        //Log.d("trace", "Image: ${articles[position].urlToImage}")
    }

    override fun getItemCount(): Int = articles.size

}

