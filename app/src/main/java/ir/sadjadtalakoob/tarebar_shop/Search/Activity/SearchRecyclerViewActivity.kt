package com.example.senthil.kotlin_recyclerview.Activity

import android.os.Bundle

import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.senthil.kotlin_recyclerview.Adapter.SearchCustomRecyclerAdapter
import com.example.senthil.kotlin_recyclerview.Model.SearchCourse
import com.example.senthil.kotlin_recyclerview.Utils.SearchHelper
import ir.sadjadtalakoob.tarebar_shop.R
import kotlinx.android.synthetic.main.activity_recycler_view.*

class SearchRecyclerViewActivity : AppCompatActivity() {

    lateinit var adapterSearch: SearchCustomRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val rvRecyclerView = findViewById<RecyclerView>(R.id.sample_recyclerView)

        rvRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        adapterSearch = SearchCustomRecyclerAdapter(this, SearchHelper.getVersionsList(this))
        rvRecyclerView.adapter = adapterSearch

        sample_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun filter(text: String) {

        val filteredSearchCourseAry: ArrayList<SearchCourse> = ArrayList()

        val searchCourseAry: ArrayList<SearchCourse> = SearchHelper.getVersionsList(this)

        for (eachCourse in searchCourseAry) {
            if (eachCourse.courseName!!.toLowerCase()
                    .contains(text.toLowerCase()) || eachCourse.courseDescrip!!.toLowerCase()
                    .contains(text.toLowerCase())
            ) {
                filteredSearchCourseAry.add(eachCourse)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapterSearch.filterList(filteredSearchCourseAry);
    }
}
