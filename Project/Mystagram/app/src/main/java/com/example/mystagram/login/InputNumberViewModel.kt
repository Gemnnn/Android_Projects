package com.example.mystagram.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class FindInModel(var id: String? = null, var phoneNumber : String? = null)

class InputNumberViewModel: ViewModel() {
    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()
    var nextPage = MutableLiveData(false)
    var inputNumber = ""

    fun savePhoneNumber(){
        var findInModel = FindInModel(auth.currentUser?.email, inputNumber)
        firestore.collection("findIds").document().set(findInModel).addOnCompleteListener{
            if(it.isSuccessful){
                nextPage.value = true
                auth.currentUser?.sendEmailVerification()
            }
        }
    }
}