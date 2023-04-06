package com.example.rsslab

import FeedAdapter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsslab.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var RSS_DEFAULT_LINK = "https://people.onliner.by/feed"
    private var RSS_link = "";

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = RSS_link
        setSupportActionBar(binding.toolbar)
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        val saveButton = findViewById<Button>(R.id.saveButton)
        val editText = findViewById<EditText>(R.id.editText)
        RSS_link = RSS_DEFAULT_LINK;
        saveButton.setOnClickListener {
            RSS_link = editText.text.toString()
            binding.toolbar.title = RSS_link;
            loadRSS()
        }
        val clearButton = findViewById<Button>(R.id.clearButton)
        clearButton.setOnClickListener {
            editText.text.clear()
            RSS_link = RSS_DEFAULT_LINK;
            binding.toolbar.title = RSS_DEFAULT_LINK;
            loadRSS()
        }
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