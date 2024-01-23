package com.example.sketch.api

import com.example.sketch.model.AccountActivation
import com.example.sketch.model.Login
import com.example.sketch.model.SignupResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by mac on 22/01/24.
 */
interface ApiInterface {
    @FormUrlEncoded
    @POST("/users/sign_in")
    @Headers("Accept:application.json")
    fun userLogin(
        @Field("user[login]") login: String,
        @Field("user[password]") password: String
    ): Call<Login>?

    @FormUrlEncoded
    @POST("/users")
    @Headers("Accept:application.json")
    fun createUsers(
        @Field("user[email]") email: String,
        @Field("user[first_name]") first_name: String,
        @Field("user[last_name]") last_name: String,
        @Field("user[company]") company: String,
        @Field("user[phone_number]") phone_number: String,
        @Field("user[address]") address: String,
        @Field("user[password]") password: String
    ): Call<SignupResponse>

    @GET("users/confirmation")
    fun verification(@Query("confirmation_token") confirmation_token: String): Call<AccountActivation>


}