/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.galacticon

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Insets.add
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.LoadRequest
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso
import ir.sadjadtalakoob.myshop.DashboardActivity.Adapter.inflate
import ir.sadjadtalakoob.tarebar_shop.Course
import ir.sadjadtalakoob.tarebar_shop.MainActivity
import ir.sadjadtalakoob.tarebar_shop.NavActivity
import ir.sadjadtalakoob.tarebar_shop.R
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import java.net.URI
import java.util.*

class RecyclerAdapter(private val context: Context, private val photos: List<Course>) :
    RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.PhotoHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return PhotoHolder(inflatedView)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: RecyclerAdapter.PhotoHolder, position: Int) {
        val itemPhoto = photos[position]

        holder.bindPhoto(itemPhoto, position)
    }


    class PhotoHolder(private val view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private var course: Course? = null


        init {
            view.setOnClickListener(this)
        }

        fun bindPhoto(course: Course, position: Int) {
            val context = itemView.context
            this.course = course
            //val uri = Uri.parse("http://torchofknowledge.ir/sb.svg")
            //Picasso.with(view.context).load(uri).into(view.lessonImage)
            //view.startRoute.load("http://torchofknowledge.ir/sb.svg")


           //var navactivity = NavActivity()
            if (position%2==0){
                GlideToVectorYou.init().with(context).load(Uri.parse("http://torchofknowledge.ir/sb.svg"),view.lessonImage)

            }else{
                GlideToVectorYou.init().with(context).load(Uri.parse("http://torchofknowledge.ir/ban.svg"),view.lessonImage)

            }
            if (position==2||position==5||position==8){
                GlideToVectorYou.init().with(context).load(Uri.parse("http://torchofknowledge.ir/bb.svg"),view.lessonImage)

            }

            if (position==3||position==6||position==9){
                GlideToVectorYou.init().with(context).load(Uri.parse("http://torchofknowledge.ir/app.svg"),view.lessonImage)

            }


            view.title_tv.text = course.lessonName
            view.courseStatus.text = position.toString()
            //Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show();
        }


        override fun onClick(v: View) {
            val context = itemView.context
            val showPhotoIntent = Intent(context, MainActivity::class.java)
            //TODO what is problem with : showPhotoIntent.putExtra(PHOTO_KEY, course)
            //showPhotoIntent.putExtra(PHOTO_KEY, course)
            context.startActivity(showPhotoIntent)
            Log.d("RecyclerView", "CLICK!")
        }

        companion object {
            private val PHOTO_KEY = "PHOTO"
        }
    }


}