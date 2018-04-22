package com.example.android.presentation.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.camera2basic.R
import kotlinx.android.synthetic.main.list_item_last_session.view.sessionTv


class LastSessionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  val list: ArrayList<String> = ArrayList<String>()
lateinit var context: Context

  fun setItems(tagArrayList: List<String>) {
    this.list.clear()
    this.list.addAll(tagArrayList)
    notifyDataSetChanged()
  }

  var onInspectionItemListener: OnInspectionItemListener? = null

  class DefaultTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
     context = parent.context
    val inflater = LayoutInflater.from(context)

    when (viewType) {
      0 -> {
        val itemView = inflater.inflate(R.layout.list_item_last_session, parent, false)

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
    holder.itemView.sessionTv.text="Inspection:"+item
    holder.itemView.setOnClickListener { onInspectionItemListener?.onInspectionClicked(item) }

  }

  override fun getItemViewType(position: Int): Int {
    return 0
  }

  override fun getItemCount(): Int {
    return list.size
  }

  interface OnInspectionItemListener {
    fun onInspectionClicked(string: String)
  }


}