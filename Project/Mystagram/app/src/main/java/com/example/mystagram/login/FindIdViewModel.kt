package com.example.mystagram.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FindIdViewModel : ViewModel() {
    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()
    var id = ""
    var phoneNumber = ""
    var toastMessage = MutableLiveData("")

    fun findMyId(){
        firestore.collection("findIds").whereEqualTo("phoneNumber", phoneNumber).get().addOnCompleteListener{
            if(it.isSuccessful && it.result.documents.size > 0){
                var findIdModel = it.result.documents.first().toObject(FindInModel::class.java)
                toastMessage.value = "Your ID is " + findIdModel?.id
            }
            else{
                toastMessage.value = "It is not valid."
            }
        }
    }

    fun findMyPassword(){
        auth.sendPasswordResetEmail(id).addOnCompleteListener{
            if(it.isSuccessful){
                toastMessage.value = "Password is reset."
            }
            else {
                toastMessage.value = "It is not valid."
            }
        }
    }
}