package com.example.senthil.kotlin_recyclerview.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.senthil.kotlin_recyclerview.Model.SearchCourse
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import ir.sadjadtalakoob.tarebar_shop.R
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class SearchCustomRecyclerAdapter(val context : Context, var searchCourseList: ArrayList<SearchCourse>) : RecyclerView.Adapter<SearchCustomRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0?.txtTitle?.text = searchCourseList[p1].courseName
        p0?.txtContent?.text = searchCourseList[p1].courseDescrip

        if (p1%2==0){
            GlideToVectorYou.init().with(context).load(Uri.parse("http://torchofknowledge.ir/sb.svg"),p0.image)

        }else{
            GlideToVectorYou.init().with(context).load(Uri.parse("http://torchofknowledge.ir/ban.svg"),p0.image)

        }
        if (p1==2||p1==5||p1==8){
            GlideToVectorYou.init().with(context).load(Uri.parse("http://torchofknowledge.ir/bb.svg"),p0.image)

        }

        if (p1==3||p1==6||p1==9){
            GlideToVectorYou.init().with(context).load(Uri.parse("http://torchofknowledge.ir/app.svg"),p0.image)

        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.recyclerview_item_row, p0, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return searchCourseList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle = itemView.findViewById<TextView>(R.id.courseStatus)
        val txtContent = itemView.findViewById<TextView>(R.id.title_tv)
        val image = itemView.findViewById<ImageView>(R.id.lessonImage)
    }

    // To get the data to search Category
    fun filterList(filteredSearchCourseList: ArrayList<SearchCourse>) {
        this.searchCourseList = filteredSearchCourseList;
        notifyDataSetChanged();
    }
}