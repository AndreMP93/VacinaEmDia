package com.example.vacinaemdia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vacinaemdia.R
import com.example.vacinaemdia.database.repository.VacinasRepository
import com.example.vacinaemdia.model.Vacina
import kotlinx.coroutines.launch

class VacinaViewModel(private val vacinasRepository: VacinasRepository) : ViewModel() {

    val listaDeVacinas = MutableLiveData<ArrayList<Vacina>>()
    val vacina = MutableLiveData<Vacina>()
    val erroManager = MutableLiveData<Int>()
    val successManager = MutableLiveData<Boolean>()


    init {
        listaDeVacinas.postValue(ArrayList<Vacina>())
    }

    fun recuperarListaDeVacinas(){
        viewModelScope.launch {
            try {
                val lista  = vacinasRepository.listarTodaAsVacinas() as ArrayList<Vacina>
                listaDeVacinas.postValue(lista)
            }catch (e: Exception){
                erroManager.postValue(R.string.aviso_erro_acesso_bd)
            }
        }
    }

    fun cadastrarNovaVacina(vacina: Vacina){
        viewModelScope.launch {
            try {
                if (vacina.nomeVacina.isNotEmpty() || vacina.nomeVacina.isNotBlank()){
                    vacinasRepository.cadastrarVacina(vacina)
                    successManager.postValue(true)
                }else{
                    erroManager.postValue(R.string.aviso_campo_obrigatorio)
                }
            }catch (e: Exception){
                erroManager.postValue(R.string.aviso_erro_cadastro_bd)
            }
        }
    }

    fun atualizarDadosVacina(vacina: Vacina){
        viewModelScope.launch {
            try {
                if (vacina.nomeVacina.isNotEmpty() || vacina.nomeVacina.isNotBlank()) {
                    vacinasRepository.atualizarVacina(vacina)
                    successManager.postValue(true)
                }else{
                    erroManager.postValue(R.string.aviso_campo_obrigatorio)
                }
            }catch (e: Exception){
                erroManager.postValue(R.string.aviso_erro_atualizacao_bd)
            }
        }
    }

    fun excluirVacina(vacina: Vacina){
        viewModelScope.launch {
            try {
                vacinasRepository.excluirVacina(vacina)
            }catch (e: Exception){
                erroManager.postValue(R.string.aviso_erro_excluir_bd)
            }
        }
    }

    fun buscaDadosVacina(id: Int){
        viewModelScope.launch {
            try {
                val v = vacinasRepository.buscarVacinaPorId(id)
                vacina.postValue(v)
            }catch (e: Exception){
                erroManager.postValue(R.string.aviso_erro_acesso_bd)
            }
        }
    }
}