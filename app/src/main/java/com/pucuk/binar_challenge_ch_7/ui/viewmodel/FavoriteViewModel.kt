package com.pucuk.binar_challenge_ch_7.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pucuk.binar_challenge_ch_7.data.local.database.FavoriteEntity
import com.pucuk.binar_challenge_ch_7.data.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val localRepository: LocalRepository) :
    ViewModel() {

    private val _favorite = MutableLiveData<List<FavoriteEntity>>()
    val favorite: LiveData<List<FavoriteEntity>> = _favorite

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    fun session() {
        if (Firebase.auth.currentUser != null) {
            _user.postValue(Firebase.auth.currentUser)
        } else {
            _user.postValue(null)
        }
    }

    fun getAllFavorites(uuid: String) = viewModelScope.launch {
        _favorite.postValue(localRepository.getFavoriteTickets(uuid))
    }
}
