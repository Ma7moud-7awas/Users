package com.m7.users.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m7.users.data.UserDAO
import com.m7.users.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userDAO: UserDAO) : ViewModel() {

    val users = userDAO.getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userDAO.insert(user)
        }
    }
}