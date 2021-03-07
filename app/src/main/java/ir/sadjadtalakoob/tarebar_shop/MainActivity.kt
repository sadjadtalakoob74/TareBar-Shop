package ir.sadjadtalakoob.tarebar_shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.galacticon.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.ArrayList

class MainActivity : AppCompatActivity()  {

    private var photosList: ArrayList<Course> = ArrayList()
    //private lateinit var imageRequester: ImageRequester
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    private val lastVisibleItemPosition: Int
        get() = if (recyclerView.layoutManager == linearLayoutManager) {
            linearLayoutManager.findLastVisibleItemPosition()
        } else {
            gridLayoutManager.findLastVisibleItemPosition()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var service: GetDataService = RetrofitClientInstance.getRetrofitInstance().create(
                GetDataService::class.java
        )
        val call = service.allCourses
        call.enqueue(object : Callback<List<Course?>?> {
            override fun onResponse(
                    call: Call<List<Course?>?>,
                    response: Response<List<Course?>?>
            ) {
                generateDataList(response.body())
                //liveFlag = true
            }

            override fun onFailure(call: Call<List<Course?>?>, t: Throwable) {
                Toast.makeText(
                        applicationContext,
                        "Something went wrong...Please try later!",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })


    }

    private fun changeLayoutManager() {
        if (recyclerView.layoutManager == linearLayoutManager) {
            //1
            recyclerView.layoutManager = gridLayoutManager
            //2
            if (photosList.size == 1) {
               // requestPhoto()
            }
        } else {
            //3
            recyclerView.layoutManager = linearLayoutManager
        }
    }

    /*private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (!imageRequester.isLoadingData && totalItemCount == lastVisibleItemPosition + 1) {
                    requestPhoto()
                }
            }
        })
    }*/

    private fun setRecyclerViewItemTouchListener() {

        //1
        val itemTouchCallback = object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                //2
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3
                val position = viewHolder.adapterPosition
                photosList.removeAt(position)
                recyclerView.adapter!!.notifyItemRemoved(position)
            }
        }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    override fun onStart() {
        super.onStart()
        if (photosList.size == 0) {
            // requestPhoto()
        }

    }

    /*private fun requestPhoto() {
        try {
            imageRequester.getPhoto()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }*/

    private fun generateDataList(courseList: List<Course?>?) {
        /* recyclerView = findViewById(R.id.customRecyclerView)
         adapter = Adapter_Course(this@Activity_Course, courseList)
         val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@Activity_Course)
         recyclerView.setLayoutManager(layoutManager)
         adapter!!.notifyDataSetChanged()
         recyclerView.setAdapter(adapter)
         progressBar!!.visibility = View.INVISIBLE*/


        // linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        adapter = RecyclerAdapter(applicationContext, courseList as List<Course>)
        recyclerView.adapter = adapter
        /* setRecyclerViewScrollListener()
         gridLayoutManager = GridLayoutManager(this, 2)
         setRecyclerViewItemTouchListener()
         imageRequester = ImageRequester(this)*/
    }

    /*override fun receivedNewPhoto(newPhoto: Course) {
        runOnUiThread {
            photosList.add(newPhoto)
            adapter.notifyItemInserted(photosList.size - 1)
        }
    }*/
}