package com.example.senthil.kotlin_recyclerview.Utils

import android.content.Context
import android.widget.Toast
import com.example.senthil.kotlin_recyclerview.Model.SearchCourse
import ir.sadjadtalakoob.tarebar_shop.Course
import ir.sadjadtalakoob.tarebar_shop.GetDataService
import ir.sadjadtalakoob.tarebar_shop.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchHelper {
    companion object {
        var lessonsList: ArrayList<Course> = ArrayList()
        var courseList = ArrayList<SearchCourse>()
        fun <ArrayList> getVersionsList(context: Context): ArrayList {

            var service: GetDataService = RetrofitClientInstance.getRetrofitInstance().create(
                GetDataService::class.java
            )
            val call = service.allCourses
            call.enqueue(object : Callback<List<Course?>?> {
                override fun onResponse(
                    call: Call<List<Course?>?>,
                    response: Response<List<Course?>?>
                ) {
                }


                override fun onFailure(call: Call<List<Course?>?>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

            return courseList as ArrayList
        }

    }
}