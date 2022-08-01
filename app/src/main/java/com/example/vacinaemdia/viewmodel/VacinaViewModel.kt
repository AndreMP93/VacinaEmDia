package com.example.vacinaemdia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vacinaemdia.database.repository.VacinasRepository
import com.example.vacinaemdia.model.Vacina
import kotlinx.coroutines.launch

class VacinaViewModel(private val vacinasRepository: VacinasRepository) : ViewModel() {

    val listaDeVacinas = MutableLiveData<ArrayList<Vacina>>()
    val vacina = MutableLiveData<Vacina>()
    val erroManager = MutableLiveData<String>()
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
                erroManager.postValue("Erro ao acessar o Banco de Dados")
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
                    erroManager.postValue("Preencha o campo do nome da vacina")
                }
            }catch (e: Exception){
                erroManager.postValue("Erro ao cadastar a vacina")
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
                    erroManager.postValue("Preencha o campo do nome da vacina")
                }
            }catch (e: Exception){
                erroManager.postValue("Erro ao Atualizar os dados da vacina")
            }
        }
    }

    fun excluirVacina(vacina: Vacina){
        viewModelScope.launch {
            try {
                vacinasRepository.excluirVacina(vacina)
            }catch (e: Exception){
                erroManager.postValue(e.message)
            }
        }
    }

    fun buscaDadosVacina(id: Int){
        viewModelScope.launch {
            try {
                val v = vacinasRepository.buscarVacinaPorId(id)
                vacina.postValue(v)
            }catch (e: Exception){
                erroManager.postValue("Falha ao acessar os dados da Vacina")
            }
        }
    }
}