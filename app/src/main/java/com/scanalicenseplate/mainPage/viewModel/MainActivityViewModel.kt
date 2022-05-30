package com.scanalicenseplate.mainPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mytaskgame.model.Repository
import com.mytaskgame.model.entities.PlateNumber
import kotlinx.coroutines.launch

class MainActivityViewModel(val repository: Repository) : ViewModel() {

    fun insertUser(plateNumber: PlateNumber) = viewModelScope.launch{
        repository.insertPlateNumberDataRoom(plateNumber)
    }
}