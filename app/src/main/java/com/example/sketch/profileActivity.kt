package com.example.sketch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class profileActivity : AppCompatActivity() {
    private lateinit var profileName: TextView
    private lateinit var profileid: TextView
    private lateinit var profilephone: TextView
    private lateinit var profileaddress: TextView
    private lateinit var profileLName: TextView
    private lateinit var profileEmail:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        profileName = findViewById(R.id.tv_profileNAme)
        profileid = findViewById(R.id.tv_profileCompany)
        profilephone = findViewById(R.id.tv_profilePhonenumber)
        profileaddress = findViewById(R.id.tv_profileAddressEntered)
        profileEmail=findViewById(R.id.tv_profileEmailenter)

        val userBundle = intent.extras
        val fname = userBundle?.getString(LoginActivity.EXTRA_FIRST_NAME)
        val lname = userBundle?.getString(LoginActivity.EXTRA_LAST_NAME)
        val id = userBundle?.getString(LoginActivity.EXTRA_ID)
        val address = userBundle?.getString(LoginActivity.EXTRA_ADDRESS)
        val phone = userBundle?.getString(LoginActivity.EXTRA_PHONE)
        val email=userBundle?.getString(LoginActivity.EXTRA_MAIL)

        profileName.text = fname
        profileid.text = id
        profilephone.text = phone
        profileaddress.text = address
        profileEmail.text=email
    }
}
