package com.codepolitan.firebaseau

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password2.*

class ForgotPasswordActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password2)

        iniActionbar()

        btnSendEmail.setOnClickListener{
            val email = etEmailForgotPassword.text.toString().trim()
            if (email.isEmpty()){
                etEmailForgotPassword.error = "Please field your email"
                etEmailForgotPassword.requestFocus()
                return@setOnClickListener
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmailForgotPassword.error = "Please user valid email"
                etEmailForgotPassword.requestFocus()
                return@setOnClickListener
            }else {
                forgotpass(email)
            }
        }
        tbForgotPassword.setNavigationOnClickListener{
            finish()
        }
    }

    private fun forgotpass(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    Toast.makeText(this, "Your reset password has been sent to your email", Toast.LENGTH_SHORT).show()
               startActivity(Intent(this, SingInActivity::class.java))
                    finishAffinity()
                }else{
                    Toast.makeText(this, "Failed reset password", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception->
                Toast.makeText(this,exception.message,Toast.LENGTH_SHORT).show()
            }
    }

    private fun iniActionbar() {
        setSupportActionBar(tbForgotPassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

    }
}