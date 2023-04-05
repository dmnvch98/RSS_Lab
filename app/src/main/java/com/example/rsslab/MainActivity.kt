package com.example.rsslab

import FeedAdapter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsslab.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val RSS_link = "https://people.onliner.by/feed"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
//        toolbar.title = "NEWS by Onliner"

        binding.toolbar.title = "NEWS by Onliner"
        setSupportActionBar(binding.toolbar)

//        setSupportActionBar(toolbar)
//        val linearLayoutManager = LinearLayoutManager(baseContext,
//            LinearLayoutManager.VERTICAL, false)
//        recyclerView.layoutManager = linearLayoutManager
//        loadRSS()
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        loadRSS()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_refresh) {
            loadRSS()
        }
        return true
    }
    private fun loadRSS() {
        val call = RetrofitServiceGenerator.createService().getFeed(RSS_link)
        call.enqueue(object : Callback<RSSObject> {
            override fun onFailure(call: Call<RSSObject>?, t: Throwable?) {
                Log.d("ResponseError", "failed")
            }
            override fun onResponse(call: Call<RSSObject>?, response:
            Response<RSSObject>?) {
                if (response?.isSuccessful == true) {
                    response.body()?.let { rssObject ->
                        Log.d("Response", "${rssObject}")
                        val adapter = FeedAdapter(rssObject, baseContext)
                        binding.recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}