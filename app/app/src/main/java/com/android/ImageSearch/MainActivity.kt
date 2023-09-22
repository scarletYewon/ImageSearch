package com.android.ImageSearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.ImageSearch.Like.Like
import com.android.ImageSearch.Search.Search
import com.android.ImageSearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var likeItem: ArrayList<Item> = ArrayList()
    private val searchF=Search()
    private val likeF=Like()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            goSearchBtn.setOnClickListener{
                setFragment(searchF)
            }
            goLikeBtn.setOnClickListener{
                setFragment(likeF)
            }
        }

        setFragment(searchF)
    }

    fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frame,frag)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    fun like(item:Item){
        if (!likeItem.contains(item)){
            likeItem.add(item)
        }
    }

    fun delLike(item:Item){
        likeItem.remove(item)
    }
}