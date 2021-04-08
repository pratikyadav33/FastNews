package com.example.fast_news

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(val context:Context,val article: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var newsImage = itemView.findViewById<ImageView>(R.id.imageView)
        var newsTitle = itemView.findViewById<TextView>(R.id.textTitle)
        var newsdesc = itemView.findViewById<TextView>(R.id.textDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article: Article = article[position]
        holder.newsTitle.text = article.title
        holder.newsdesc.text = article.description
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)
        holder.itemView.setOnClickListener {

            Toast.makeText(context,article.title,Toast.LENGTH_SHORT).show()

            val intent = Intent(context,DetailActivity::class.java)
            intent.putExtra("URL",article.url)
            context.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
        return article.size
    }


}