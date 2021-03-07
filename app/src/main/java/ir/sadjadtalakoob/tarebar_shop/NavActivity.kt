package ir.sadjadtalakoob.tarebar_shop

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.senthil.kotlin_recyclerview.Adapter.SearchCustomRecyclerAdapter
import com.example.senthil.kotlin_recyclerview.Model.SearchCourse
import com.example.senthil.kotlin_recyclerview.Utils.SearchHelper
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.*
import com.mikepenz.materialdrawer.util.setupWithNavController
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.mikepenz.materialdrawer.widget.MaterialDrawerSliderView
import com.raywenderlich.galacticon.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.sample_editText
import kotlinx.android.synthetic.main.recyclerview_item_row.*
import kotlinx.android.synthetic.main.recyclerview_item_row.lessonImage
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.ArrayList

class NavActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var photosList: ArrayList<Course> = ArrayList()

    //private lateinit var imageRequester: ImageRequester
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    lateinit var adapterSearch: SearchCustomRecyclerAdapter
    var lessonName: String = ""
    var lessonsList: ArrayList<Course> = ArrayList()

    private val lastVisibleItemPosition: Int
        get() = if (recyclerView.layoutManager == linearLayoutManager) {
            linearLayoutManager.findLastVisibleItemPosition()
        } else {
            gridLayoutManager.findLastVisibleItemPosition()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL)

        //Retrofit
        var service: GetDataService = RetrofitClientInstance.getRetrofitInstance().create(
            GetDataService::class.java
        )
        val call = service.allCourses
        call.enqueue(object : Callback<List<Course?>?> {
            override fun onResponse(
                call: Call<List<Course?>?>,
                response: Response<List<Course?>?>
            ) {
                //generateDataList(response.body())

                for (course in response.body()!!) {

                    if (course != null) {
                        SearchHelper.lessonsList.add(course)
                        println(course.lessonName)
                        SearchHelper.courseList.add(
                            SearchCourse(
                                course.lessonName,
                                ""
                            )
                        )
                    }
                }

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
        //

        Handler().postDelayed(
            {
                val rvRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

                //rvRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
                rvRecyclerView.layoutManager = gridLayoutManager

                adapterSearch =
                    SearchCustomRecyclerAdapter(this, SearchHelper.getVersionsList(this))
                rvRecyclerView.adapter = adapterSearch
            },
            2000 // value in milliseconds
        )



        sample_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })


        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: MaterialDrawerSliderView = findViewById(R.id.slider)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //if you want to update the items at a later time it is recommended to keep it in a variable
        val item1 = PrimaryDrawerItem().apply { nameRes = R.string.one; identifier = 1 }
        val item2 = SecondaryDrawerItem().apply { nameRes = R.string.two; identifier = 2 }

// get the reference to the slider and add the items
        slider.itemAdapter.add(
            item1,
            DividerDrawerItem(),
            item2,
            SecondaryDrawerItem().apply { nameRes = R.string.three }
        )

// specify a click listener
        slider.onDrawerItemClickListener = { v, drawerItem, position ->
            // do something with the clicked item :D
            false
        }

        // Create the AccountHeader
        var headerView = AccountHeaderView(this).apply {
            attachToSliderView(slider) // attach to the slider
            addProfiles(
                ProfileDrawerItem().apply {
                    nameText = "Mike Penz"; descriptionText = "mikepenz@gmail.com"; iconUrl =
                    "https://avatars3.githubusercontent.com/u/1476232?v=3&s=460"; identifier = 100
                }

            )
            onAccountHeaderListener = { view, profile, current ->
                // react to profile changes
                false
            }
            withSavedInstance(savedInstanceState)
        }
        val imageSlider = findViewById<ImageSlider>(R.id.image_slider) // init imageSlider

        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(
            SlideModel(
                "https://img9.irna.ir/old/Image/1398/13980216/83304833/N83304833-73011184.jpg",
                "TareBar1",
                ScaleTypes.CENTER_CROP
            )
        )
        imageList.add(
            SlideModel(
                "https://www.iribnews.ir/files/fa/news/1397/4/26/2409837_787.jpg",
                "TareBar2",
                ScaleTypes.CENTER_CROP
            )
        )
        imageList.add(
            SlideModel(
                "https://media.hamshahrionline.ir/d/2020/02/17/4/4398661.jpg",
                "TareBar3",
                ScaleTypes.CENTER_CROP
            )
        )

        imageSlider.setImageList(imageList)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                // You can listen here.
                println("Pos: " + position + " clicked")
            }
        })

        imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                println("Pos: " + position)
            }
        })

        imageSlider.setTouchListener(object : TouchListener {
            override fun onTouched(touched: ActionTypes) {
                if (touched == ActionTypes.DOWN) {
                    imageSlider.stopSliding()
                } else if (touched == ActionTypes.UP) {
                    imageSlider.startSliding(1000)
                }
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    /*private fun changeLayoutManager() {
        if (recyclerView.layoutManager == linearLayoutManager) {
            //1
            recyclerView.layoutManager = gridLayoutManager
            //2
            if (photosList.size == 1) {
                requestPhoto()
            }
        } else {
            //3
            recyclerView.layoutManager = linearLayoutManager
        }
    }*/

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

    /*  private fun requestPhoto() {
          try {
              imageRequester.getPhoto()
          } catch (e: IOException) {
              e.printStackTrace()
          }

      }*/

    private fun generateDataList(courseList: List<Course?>?) {

        gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        adapter = RecyclerAdapter(applicationContext, courseList as List<Course>)
        recyclerView.adapter = adapter

    }

    /*override fun receivedNewPhoto(newPhoto: Course) {
        runOnUiThread {
            photosList.add(newPhoto)
            adapter.notifyItemInserted(photosList.size - 1)
        }
    }*/
}