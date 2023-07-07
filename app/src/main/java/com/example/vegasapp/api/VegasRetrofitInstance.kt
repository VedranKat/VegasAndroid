package com.example.vegasapp.api

import com.example.vegasapp.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VegasRetrofitInstance {

    companion object{
        private val retrofit by lazy{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            //add token interceptor later on
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl("http://"+Constants.VEGAS_IP+":8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy{
            retrofit.create(VegasAPI::class.java)
        }
    }
}