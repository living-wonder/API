package com.example.sketch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.sketch.api.*
import com.example.sketch.model.AccountActivation
import com.example.sketch.model.SignupResponse
import com.example.sketch.model.UserRequest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {
     private lateinit var signUpFirstName:EditText
    private lateinit var signUpLastName:EditText
    private lateinit var signUpEmail:EditText
    private lateinit var signUpPhone:EditText
    private lateinit var signUpPassword:EditText
    private lateinit var signUpCPassword:EditText
    private lateinit var signUpAddress:EditText
    private lateinit var signUpCompany:EditText
    private lateinit var save:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signUpFirstName = findViewById(R.id.et_firstName)
        signUpLastName = findViewById(R.id.et_lastName)
        signUpEmail = findViewById(R.id.et_email)
        signUpPhone = findViewById(R.id.et_phone)
        signUpPassword = findViewById(R.id.et_password)
        signUpCPassword = findViewById(R.id.et_confirmPassword)
        signUpAddress = findViewById(R.id.et_street1)
        signUpCompany = findViewById(R.id.et_company)

        save = findViewById(R.id.tv_save)


        //action
        save.setOnClickListener {

            val sfname = signUpFirstName.text.toString().trim()
            val slname = signUpLastName.text.toString().trim()
            val smail = signUpEmail.text.toString().trim()
            val saddress = signUpAddress.text.toString().trim()
            val sphone = signUpPhone.text.toString().trim()
            val spassword = signUpPassword.text.toString().trim()
            val scpassword = signUpCPassword.text.toString().trim()
            val scompany = signUpCompany.text.toString().trim()

            if (sfname.isEmpty()) {
                signUpFirstName.error = "First Name Required"
                signUpFirstName.requestFocus()
                return@setOnClickListener
            }
            if (slname.isEmpty()) {
                signUpLastName.error = "Last Name Required"
                signUpLastName.requestFocus()
                return@setOnClickListener
            }
            if (sfname.isEmpty()) {
                signUpFirstName.error = "First Name Required"
                signUpFirstName.requestFocus()
                return@setOnClickListener
            }
            if (smail.isEmpty()) {
                signUpEmail.error = "Email Required"
                signUpEmail.requestFocus()
                return@setOnClickListener
            }
            if (saddress.isEmpty()) {
                signUpAddress.error = "Address Required"
                signUpAddress.requestFocus()
                return@setOnClickListener
            }
            if (sphone.isEmpty()) {
                signUpPhone.error = "Phone Number Required"
                signUpPhone.requestFocus()
                return@setOnClickListener
            }
            if (spassword.isEmpty()) {
                signUpPassword.error = "Password Required"
                signUpPassword.requestFocus()
                return@setOnClickListener
            }
            if (scpassword.isEmpty()) {
                signUpCPassword.error = " Confirm Password Required"
                signUpCPassword.requestFocus()
                return@setOnClickListener
            }
            if (scompany.isEmpty()) {
                signUpCompany.error = "Company Required"
                signUpCompany.requestFocus()
                return@setOnClickListener
            }
            if (!arePasswordsEqual(spassword, scpassword)) {
                Toast.makeText(this, "Password must equal", Toast.LENGTH_SHORT).show()
                signUpPassword.requestFocus()
                return@setOnClickListener
            }


            val retrofit = Apiclient.getinstance()?.create(ApiInterface::class.java)
            val call=retrofit?.createUsers(smail,sfname,slname,scompany,sphone,saddress,spassword)
            call?.enqueue(object:Callback<SignupResponse>{
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    if(response.isSuccessful){
                        val responseBody=response.body()

                        Toast.makeText(this@SignUp, "Sign Up Successfull", Toast.LENGTH_SHORT).show()
                        showOtpPopup(this@SignUp)

                    }else{
                        val error=response.errorBody().toString()
                        Log.d("Error","Error message $error")

                    }

                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    val message = t.message
                    Log.d("Error","Error message $message")
                    Toast.makeText(this@SignUp, "Sign Up Failed", Toast.LENGTH_SHORT).show()


                }

            })



        }




    }
    private fun arePasswordsEqual(password: String, ConfirmPassword: String): Boolean {
        return password == ConfirmPassword
    }

    private fun showOtpPopup(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.verification, null)
        val editOtp = dialogView.findViewById<EditText>(R.id.ev_otp)  // Corrected
        val buttonVerify = dialogView.findViewById<TextView>(R.id.tv_okverification)  // Corrected
        val alertDialog =
            AlertDialog.Builder(context).setView(dialogView).setCancelable(false).create()
        alertDialog.show()

        buttonVerify.setOnClickListener {
            val retrofit = Apiclient.getinstance()?.create(ApiInterface::class.java)
            val otp = editOtp.text.toString().trim()
            val verify = retrofit?.verification(otp)
            verify?.enqueue(object : Callback<AccountActivation> {
                override fun onResponse(
                    call: Call<AccountActivation>,
                    response: Response<AccountActivation>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SignUp, "Verification Successful", Toast.LENGTH_SHORT)
                            .show()

                        finish()
                    } else {
                        val error = response.errorBody()
                        Log.d("Error", "Error message $error")

                    }
                }

                override fun onFailure(call: Call<AccountActivation>, t: Throwable) {
                    // Handle failure case
                }
            })
        }
    }}

