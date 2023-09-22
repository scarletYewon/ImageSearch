package com.android.ImageSearch.Like

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.ImageSearch.Item
import com.android.ImageSearch.MainActivity
import com.android.ImageSearch.databinding.ItemBinding
import com.bumptech.glide.Glide
import com.android.ImageSearch.util.Utils
import com.android.ImageSearch.util.Utils.getDateFromTimestampWithFormat

class LikeAdapter(var myContext:Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(myContext)
            .load(items[position].url)
            .into((holder as ItemViewHolder).thum)

        holder.title.text = items[position].title
        holder.like.visibility = View.GONE
        holder.date.text = getDateFromTimestampWithFormat(
            items[position].date,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd HH:mm:ss"
        )
    }

    inner class ItemViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){
        var thum: ImageView = binding.itemImg
        var like: ImageView = binding.itemHeart
        var title: TextView = binding.itemName
        var date: TextView = binding.itemTime
        var layout: ConstraintLayout = binding.itemLayout

        init {
            like.visibility = View.GONE
            layout.setOnClickListener{
                val position = adapterPosition
                if (position!=RecyclerView.NO_POSITION){
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
}