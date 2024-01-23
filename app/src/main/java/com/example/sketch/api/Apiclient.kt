package com.example.sketch.api

/**
 * Created by mac on 22/01/24.
 */
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Apiclient {
    var retrofit:Retrofit?=null
    fun getinstance():Retrofit{
        val builder =Retrofit.Builder().baseUrl("https://contact-lists-api.onrender.com").addConverterFactory(GsonConverterFactory.create()).build()
        retrofit=builder
        return retrofit!!
    }
}