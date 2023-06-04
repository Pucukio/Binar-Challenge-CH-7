package com.pucuk.binar_challenge_ch_7.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pucuk.binar_challenge_ch_7.data.network.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val _register = MutableLiveData<String>()
    val register: LiveData<String> = _register
    fun registerFirebase(_email: String, _password: String) =
        Firebase.auth.createUserWithEmailAndPassword(_email, _password).addOnCompleteListener {
            if (it.isSuccessful) {
                _register.postValue("Registration Successful!")
            } else {
                _register.postValue(it.exception.toString())
            }
        }
}