package com.codepolitan.firebaseau

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)

        Handler().postDelayed({
            checkAuth()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }, 1200)
    }

    private fun checkAuth() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }
}
