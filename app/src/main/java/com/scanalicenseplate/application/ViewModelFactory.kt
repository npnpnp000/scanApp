package com.scanalicenseplate.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mytaskgame.model.Repository
import com.scanalicenseplate.mainPage.viewModel.MainActivityViewModel

@Suppress("UNCHECKED_CAST")
    class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            when(modelClass){
                MainActivityViewModel::class.java ->{ MainActivityViewModel(repository) as T }
                else ->  throw Exception("Not define view model class" )
            }



    }
