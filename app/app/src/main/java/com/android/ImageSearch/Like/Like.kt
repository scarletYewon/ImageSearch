package com.android.ImageSearch.Like

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.ImageSearch.Item
import com.android.ImageSearch.MainActivity
import com.android.ImageSearch.databinding.FragmentLikeBinding

class Like : Fragment() {

    private lateinit var binding: FragmentLikeBinding
    private lateinit var myContext: Context
    private lateinit var adapter: LikeAdapter
    private var likeItem: List<Item> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val main = activity as MainActivity
        likeItem = main.likeItem

        adapter = LikeAdapter(myContext).apply {
            items = likeItem.toMutableList()
        }

        binding = FragmentLikeBinding.inflate(inflater,container,false).apply {
            likeList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            likeList.adapter = adapter

        }

        return binding.root
    }

}