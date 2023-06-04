package com.pucuk.binar_challenge_ch_7.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pucuk.binar_challenge_ch_7.data.model.ResponseFilm
import com.pucuk.binar_challenge_ch_7.data.network.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var api : ApiClient): ViewModel() {
    private val _movie = MutableLiveData<ResponseFilm>()
    val movie: LiveData<ResponseFilm> = _movie

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    fun getFilm() = viewModelScope.launch {
        _movie.postValue(api.getAllFilmPopular())
    }


    fun session() {
        if (Firebase.auth.currentUser != null) {
            _user.postValue(Firebase.auth.currentUser)
        } else {
            _user.postValue(null)
        }
    }
}