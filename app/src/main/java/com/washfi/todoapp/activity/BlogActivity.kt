package com.washfi.todoapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.washfi.todoapp.R
import com.washfi.todoapp.adapter.BlogAdapter
import com.washfi.todoapp.model.JsonResponse


class BlogActivity : AppCompatActivity() {
    lateinit var recyclerViewBlogs: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogs()
    }

    private fun getBlogs() {
        AndroidNetworking.get("http://mocky.io/v2/5926ce9d11000096006ccb30")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(JsonResponse::class.java,object : ParsedRequestListener<JsonResponse>{
                    override fun onResponse(response: JsonResponse?) {
                        setUpRecyclerView(response)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d("BlogActivity", anError?.localizedMessage.toString())
                    }

                })
    }

    private fun setUpRecyclerView(response: JsonResponse?) {
        val blogAdapter = BlogAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogAdapter
    }

    private fun bindViews() {
        recyclerViewBlogs = findViewById(R.id.recyclerViewBlogs)
    }
}