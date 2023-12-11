package com.example.mystagram.login

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mystagram.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var auth = FirebaseAuth.getInstance()
    var id : MutableLiveData<String> = MutableLiveData("")
    var password :MutableLiveData<String> = MutableLiveData("")



    var showInputNumberActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindIdActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    val context = getApplication<Application>().applicationContext

    var googleSignInClient : GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context , gso)
    }


    fun loginWithSignupEmail(){
        println("Email")
        auth.createUserWithEmailAndPassword(id.value.toString(), password.value.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                showInputNumberActivity.value = true

            }
            else{

            }
        }
    }

    fun loginGoogle(view: View){
        var i = googleSignInClient.signInIntent
        (view.context as? LoginActivity)?.googleLoginResult?.launch(i)
    }


    fun firebaseAuthWithGoogle(idToken : String?){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener() {
            if(it.isSuccessful){
                showInputNumberActivity.value = true

            }
            else{

            }
        }
    }

}