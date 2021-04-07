package com.example.fast_news

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
    private var article = mutableListOf<Article>()
    var totalResult = -1
    var pageNum = 1
    var col = ColorPicker.getColor()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = NewsAdapter(this@MainActivity,article)
        newList.adapter = adapter

        //newList.layoutManager= LinearLayoutManager(this@MainActivity)

        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)

        layoutManager.setPagerMode(true)

        layoutManager.setPagerFlingVelocity(3000)

        layoutManager.setItemChangedListener(object:StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
                col = ColorPicker.getColor()
                card_main.setBackgroundColor(Color.parseColor(col))

                if( totalResult > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount-5){
                    pageNum++
                    getNews()
                }
            }

        })


        newList.layoutManager = layoutManager

        getNews()


    }

    private fun getNews() {
        val news: Call<News> = NewsService.newsInstance.getHeadlines( "in", pageNum)

        news.enqueue(object : Callback<News> {

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Pratik", "Error ", t)
            }


            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news: News? = response.body()

                if (news != null) {
                    totalResult=news.totalResult
                    Log.d("Pratik", news.toString())
                    article.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }


        })

    }

}