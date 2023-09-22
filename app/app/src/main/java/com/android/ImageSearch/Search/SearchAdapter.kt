package com.android.ImageSearch.Search

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
import com.android.ImageSearch.util.Utils.getDateFromTimestampWithFormat
import com.bumptech.glide.Glide



class SearchAdapter (private val myContext: Context): RecyclerView.Adapter<SearchAdapter.ItemViewHolder>(){

    var items = ArrayList<Item>()

    fun clearItem(){
        items.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        Glide.with(myContext)
            .load(currentItem.url)
            .into(holder.thum)

        holder.like.visibility = if (currentItem.like) View.VISIBLE else View.INVISIBLE
        holder.title.text = currentItem.title
        holder.date.text = getDateFromTimestampWithFormat(
            currentItem.date,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd HH:mm:ss"
        )
    }

    override fun getItemCount() = items.size
    inner class ItemViewHolder(binding: ItemBinding):RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        var thum: ImageView = binding.itemImg
        var like:ImageView = binding.itemHeart
        var title:TextView = binding.itemName
        var date:TextView = binding.itemTime
        var layout:ConstraintLayout = binding.itemLayout
        init {
            like.visibility = View.GONE
            thum.setOnClickListener(this)
            layout.setOnClickListener(this)
        }
        override fun onClick(view: View) {
            val position = adapterPosition.takeIf {
                it != RecyclerView.NO_POSITION
            }?: return
            val item = items[position]
            item.like = !item.like
            if (item.like){
                (myContext as MainActivity).like(item)
            } else {
                (myContext as MainActivity).delLike(item)
            }

            notifyItemChanged(position)
        }
    }
}

