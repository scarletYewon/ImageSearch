package com.android.ImageSearch.data

import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.android.ImageSearch.data.api.RetrofitInterface
import com.android.ImageSearch.util.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitClient{
    val apiService:RetrofitInterface
        get() = instance.create(RetrofitInterface::class.java)

    private val instance: Retrofit
        private get() {
            val gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}