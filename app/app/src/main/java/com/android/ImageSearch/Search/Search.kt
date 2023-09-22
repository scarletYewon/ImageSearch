package com.android.ImageSearch.Search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.ImageSearch.Item
import com.android.ImageSearch.data.model.ImageModel
import com.android.ImageSearch.data.retrofitClient.apiService
import com.android.ImageSearch.databinding.FragmentSearchBinding
import com.android.ImageSearch.util.Constants
import com.android.ImageSearch.util.Utils.getLastSearch
import com.android.ImageSearch.util.Utils.saveLastSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Search : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var myContext: Context
    private lateinit var adapter: SearchAdapter
    private lateinit var gridM:StaggeredGridLayoutManager
    private var searchResultList: ArrayList<Item> = ArrayList()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        setView()
        setupListeners()
        return binding.root
    }

    fun setView(){
        gridM = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.searchResult.layoutManager = gridM
        adapter = SearchAdapter(myContext)
        binding.searchResult.adapter = adapter
        binding.searchResult.itemAnimator = null

        val lastSearch = getLastSearch(requireContext())
        binding.searchKey.setText(lastSearch)
    }
    private fun setupListeners() {
        binding.searchBtn.setOnClickListener{
            val key = binding.searchKey.text.toString()
            if (key.isNotEmpty()) {
                saveLastSearch(requireContext(), key)
                adapter.clearItem()
                getImageRST(key)
            } else {
                Toast.makeText (myContext,"검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.searchKey.windowToken,0)
        }
    }

    private fun getImageRST(key: String) {
        apiService.imageSearch(Constants.AUTH_HEADER, key, "recency", 1, 80)?.enqueue(object : Callback<ImageModel?> {
            override fun onResponse(call: Call<ImageModel?>, response: Response<ImageModel?>) {
                response.body()?.meta?.let{ meta->

                    if (meta.totalCount > 0){
                        Log.e("ddd",meta?.totalCount.toString())
                        Log.e("ddd",response.body()?.documents?.size.toString())
                        response.body()?.documents?.forEach { document ->
                            Log.e("ddd",document.displaySitename)
                            val title = document.displaySitename
                            val datetime = document.datetime
                            val url = document.thumbnailUrl
                            searchResultList.add(Item(title,datetime,url))
                        }
                    }
                }
                adapter.items = searchResultList
                adapter.notifyDataSetChanged()
                Log.e("items",adapter.items.size.toString())
            }

            override fun onFailure(call: Call<ImageModel?>, t: Throwable) {
                Log.d("result", "${t.message}")
            }

        })
    }
}