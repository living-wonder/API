package com.example.sketch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sketch.api.ApiInterface
import com.example.sketch.api.Apiclient
import com.example.sketch.model.Login
import retrofit2.Call
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_FOR_SIGNUP = 1
        const val EXTRA_FIRST_NAME = "extra_first_name"
        const val EXTRA_LAST_NAME = "extra_last_name"
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_ADDRESS = "extra_address"
        const val EXTRA_MAIL="extra_mail"
    }

    private lateinit var buttonLogin: Button
    private lateinit var signup: TextView
    private lateinit var loginUserName: EditText
    private lateinit var loginPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin = findViewById(R.id.btn_login)
        loginUserName = findViewById(R.id.et_email)
        loginPassword = findViewById(R.id.et_password)
        signup = findViewById(R.id.tv_signUp)

        buttonLogin.setOnClickListener {
            val username = loginUserName.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if (username.isEmpty()) {
                loginUserName.error = "Email Required"
                loginUserName.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                loginPassword.error = "Password Required"
                loginPassword.requestFocus()
                return@setOnClickListener
            }

            val retrofit = Apiclient.getinstance().create(ApiInterface::class.java)
            val userlogin = retrofit?.userLogin(username, password)

            userlogin?.enqueue(object : retrofit2.Callback<Login> {
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    if (response.isSuccessful) {
                        handleSuccessfulLogin(response.body())
                    } else {
                        Toast.makeText(applicationContext, "LOGIN FAILED", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Login>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }

        signup.setOnClickListener {
            startActivityForResult(
                Intent(this@LoginActivity, SignUp::class.java),
                REQUEST_CODE_FOR_SIGNUP
            )
        }
    }

    private fun handleSuccessfulLogin(profile: Login?) {
        profile?.User?.let { user ->
            val profileIntent = Intent(this@LoginActivity, profileActivity::class.java).apply {
                val userBundle = Bundle().apply {
                    putString(LoginActivity.EXTRA_FIRST_NAME, user.first_name)
                    putString(LoginActivity.EXTRA_LAST_NAME, user.last_name)
                    putString(LoginActivity.EXTRA_PHONE, user.phone_number)
                    putString(LoginActivity.EXTRA_ID, user.id.toString()) // Convert Int to String
                    putString(LoginActivity.EXTRA_ADDRESS, user.address)
                    putString(LoginActivity.EXTRA_MAIL,user.email)
                }
                putExtras(userBundle)
            }

            startActivity(profileIntent)
            finish()
        }
    }
}
