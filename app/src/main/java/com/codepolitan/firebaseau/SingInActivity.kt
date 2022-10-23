package com.codepolitan.firebaseau

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sing_in.*

class SingInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private  lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object{
        const val RC_SIGN_IN = 100
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)

        iniActionBar()
        iniFirebaseAuth()

        btnSingIn.setOnClickListener{
            val email =etEmailSingIn.text.toString().trim()
            val pass = etPasswordSingIn.text.toString().trim()

            if (checkValidation(email, pass)){
                loginToServer(email, pass)
            }
        }
        btnForgotPass.setOnClickListener{
            startActivity(Intent(this, ForgotPasswordActivity2::class.java))
        }
        tbSingIn.setNavigationOnClickListener(){
            finish()

        }
        btnGoogleSingIn.setOnClickListener(){
        val signIntent =mGoogleSignInClient.signInIntent
            startActivityForResult(signIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            CustomDialog.showLoading(this)
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                firebaseAuth(credential)
            } catch (e: ApiException){
                CustomDialog.hideLoading()
                Toast.makeText(this, "Sing-In Cancelled", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun loginToServer(email: String, pass: String) {
        val credential = EmailAuthProvider.getCredential(email, pass)
        firebaseAuth(credential)
    }

//    FUNSI UNTUK MENCOCOKAN EMAIL DAN PASWORD DISERVER APAKAH BISA MASUK KESISTEM
//    MULAI
    private fun firebaseAuth(credential: AuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                CustomDialog.hideLoading()
                if (task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }else{
                    Toast.makeText(this, "Sign-In Failed", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {exception ->
                CustomDialog.hideLoading()
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
    }
//    SELESAI

    //        coding untuk email dan password salah MULAI
    private fun checkValidation(email: String, pass: String): Boolean {
        if (email.isEmpty()){
            etEmailSingIn.error = "Please field your email"
            etEmailSingIn.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmailSingIn.requestFocus()
        }else if (pass.isEmpty()){
            etPasswordSingIn.error ="Please field your password"
            etPasswordSingIn.requestFocus()
        }else{
            return true
        }
        CustomDialog.hideLoading()
        return false
    }
    //        coding untuk email dan password salah SELESAI

    private fun iniFirebaseAuth() {
        auth = FirebaseAuth.getInstance()

//        configurasi google sign in
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    private fun iniActionBar() {
//        FUNSING UNTUK MEMUNCULKAN BUTTOM KEMBALI
//        kalau tbSingIn merah tidak bisa diALT+enter itu harus import pluging dulu digradle modul     id 'kotlin-android-extensions'
        setSupportActionBar(tbSingIn)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}