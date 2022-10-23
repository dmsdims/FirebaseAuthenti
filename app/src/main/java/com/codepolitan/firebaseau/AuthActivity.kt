package com.codepolitan.firebaseau

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_auth2.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth2)

        btnSingInAuth.setOnClickListener {
            startActivity(Intent(this, SingInActivity::class.java))
        }
        btnSingUpAuth.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

}
