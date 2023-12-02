package com.example.mystagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null


    lateinit var btn_login: Button
    lateinit var et_email: EditText
    lateinit var et_password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btn_login = findViewById(R.id.btn_login)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)

        btn_login.setOnClickListener {
            signinAndSignup()
        }
    }

    fun signinAndSignup(){
        auth?.createUserWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
            ?.addOnCompleteListener{
                task ->
                    if(task.isSuccessful){
                        // Createing a user account
                        moveMainPage(task.result.user)
                    }
                    else if(!task.exception?.message.isNullOrEmpty()){
                        // Catch an error
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                    else{
                        // Login, if the user has an account
                        signinEmail()
                    }
            }
    }

    fun signinEmail(){
        auth?.signInWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
            ?.addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    // Login
                    moveMainPage(task.result.user)
                }
                else{
                    // Show an error
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()

                }
            }
    }

    fun moveMainPage(user: FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}