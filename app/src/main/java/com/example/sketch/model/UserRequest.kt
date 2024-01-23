package com.example.sketch.model

/**
 * Created by mac on 22/01/24.
 */
data class UserRequest(    val email: String,
                           val first_name: String,
                           val last_name: String,
                           val company:String,
                           val phone_number: String,
                           val address: String,
                           val password: String,
)
