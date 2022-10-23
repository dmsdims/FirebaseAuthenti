package com.codepolitan.firebaseau

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var mGoogleSignInClient:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniFirebaseAuth()
        getData()

        btnLogout.setOnClickListener{
            auth.signOut()
            mGoogleSignInClient.signOut().addOnCompleteListener{
                Toast.makeText(this, "Success Logout", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this,AuthActivity::class.java))
            finish()
        }
    }

    private fun getData() {
        val user = auth.currentUser
        if (user !=null){
            tvEmail.text = user.email
        }
    }

    private fun iniFirebaseAuth() {
        auth =FirebaseAuth.getInstance()

        //        configurasi google sign in
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }
}