package com.example.android.presentation

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.camera2basic.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_gallery.view.imageIv
import java.io.Console
import java.io.File


class EventAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  val list: ArrayList<File> = ArrayList<File>()
lateinit var context: Context

  fun setItems(tagArrayList: List<File>) {
    this.list.addAll(tagArrayList)
    notifyDataSetChanged()
  }

  var onDocsItemClickListener: OnTagClickListener? = null

  class DefaultTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
     context = parent.context
    val inflater = LayoutInflater.from(context)

    when (viewType) {
      0 -> {
        val itemView = inflater.inflate(R.layout.list_item_gallery, parent, false)

        return DefaultTagViewHolder(itemView)
      }
    }

    throw RuntimeException(
      "there is no type that matches the type "
          + viewType
          + " + make sure your using types correctly"
    )
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    if (holder is DefaultTagViewHolder) {
      setupDefaultTagViewHolder(holder, position)
    }
  }

  private fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
    val item = list[position]
    val bmImg = BitmapFactory.decodeFile(item.path)
    Picasso.with(context).load(item).into(holder.itemView.imageIv)


  }

  override fun getItemViewType(position: Int): Int {
    return 0
  }

  override fun getItemCount(): Int {
    return list.size
  }

  interface OnTagClickListener {

  }


}