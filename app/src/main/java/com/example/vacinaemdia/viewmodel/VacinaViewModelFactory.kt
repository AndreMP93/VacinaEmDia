package com.example.vacinaemdia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vacinaemdia.database.repository.VacinasRepository

class VacinaViewModelFactory(private val vacinasRepository: VacinasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if( modelClass.isAssignableFrom(VacinaViewModel::class.java)){
            VacinaViewModel(this.vacinasRepository) as T
        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}