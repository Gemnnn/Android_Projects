package com.example.mystagram.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mystagram.R
import com.example.mystagram.databinding.ActivityLoginBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays


class LoginActivity : AppCompatActivity() {
    var TAG = "LoginActivity"
    lateinit var binding: ActivityLoginBinding
    val loginViewModel: LoginViewModel by viewModels()
    private lateinit var auth : FirebaseAuth
    lateinit var callbackManager: CallbackManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
//        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = loginViewModel
        binding.activity = this
        binding.lifecycleOwner = this
        callbackManager = CallbackManager.Factory.create()
        setObserve()
        printHashKey(this)
    }

    fun loginFacebook(){
        var loginManager = LoginManager.getInstance()
        loginManager.loginBehavior = LoginBehavior.WEB_ONLY
        loginManager.logInWithReadPermissions(this, Arrays.asList("email"))
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
            }

            override fun onSuccess(result: LoginResult) {
                val token = result.accessToken
                loginViewModel.firebaseAuthWithFacebook(token)
            }

        })
    }

    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i(TAG, "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "printHashKey()", e)
        } catch (e: Exception) {
            Log.e(TAG, "printHashKey()", e)
        }
    }

    fun setObserve(){
        loginViewModel.showInputNumberActivity.observe(this){
            if (it){
                finish()
                startActivity(Intent(this, InputNumberActivity::class.java))
            }
        }

        loginViewModel.showFindIdActivity.observe(this){
            if (it){
                startActivity(Intent(this, FindIdActivity::class.java))
            }
        }
    }
    fun loginEmail() {
        loginViewModel.showInputNumberActivity.value = true
    }

    fun findId(){
        loginViewModel.showFindIdActivity.value = true

    }

    var googleLoginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->

        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        account.idToken

        loginViewModel.firebaseAuthWithGoogle(account.idToken)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    
}