package com.example.vacinaemdia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vacinaemdia.R
import com.example.vacinaemdia.database.repository.VacinasRepository
import com.example.vacinaemdia.model.Vacina
import com.example.vacinaemdia.model.ValidacaoModel
import kotlinx.coroutines.launch

class VacinaViewModel(
    private val application: Application,
    private val vacinasRepository: VacinasRepository
) : AndroidViewModel(application) {

    private val _listaDeVacinas = MutableLiveData<ArrayList<Vacina>>()
    val listaDeVacinas: LiveData<ArrayList<Vacina>> = _listaDeVacinas
    private val _vacina = MutableLiveData<Vacina>()
    val vacina: LiveData<Vacina> = _vacina

    private val _validacao = MutableLiveData<ValidacaoModel>()
    val validacao: LiveData<ValidacaoModel> = _validacao

    init {
        _listaDeVacinas.postValue(ArrayList<Vacina>())
    }

    fun recuperarListaDeVacinas() {
        viewModelScope.launch {
            try {
                val lista = vacinasRepository.listarTodaAsVacinas() as ArrayList<Vacina>
                _listaDeVacinas.postValue(lista)
            } catch (e: Exception) {
                _validacao.value = ValidacaoModel(application.getString(R.string.aviso_erro_acesso_bd))
            }
        }
    }

    fun cadastrarNovaVacina(vacina: Vacina) {
        viewModelScope.launch {
            try {
                if (vacina.nomeVacina.isNotEmpty() || vacina.nomeVacina.isNotBlank()) {
                    vacinasRepository.cadastrarVacina(vacina)
                    _validacao.postValue(ValidacaoModel())
                } else {
                    _validacao.postValue(ValidacaoModel(application.getString(R.string.aviso_campo_obrigatorio)))
                }
            } catch (e: Exception) {
                _validacao.postValue(ValidacaoModel(application.getString(R.string.aviso_erro_cadastro_bd)))
            }
        }
    }

    fun atualizarDadosVacina(vacina: Vacina) {
        viewModelScope.launch {
            try {
                if (vacina.nomeVacina.isNotEmpty() || vacina.nomeVacina.isNotBlank()) {
                    vacinasRepository.atualizarVacina(vacina)
                    _validacao.postValue(ValidacaoModel())
                } else {
                    _validacao.postValue(ValidacaoModel(application.getString(R.string.aviso_campo_obrigatorio)))
                }
            } catch (e: Exception) {
                _validacao.postValue(ValidacaoModel(application.getString(R.string.aviso_erro_atualizacao_bd)))
            }
        }
    }

    fun excluirVacina(vacina: Vacina) {
        viewModelScope.launch {
            try {
                vacinasRepository.excluirVacina(vacina)
                _validacao.value = ValidacaoModel()
            } catch (e: Exception) {
                _validacao.value = ValidacaoModel(application.getString(R.string.aviso_erro_excluir_bd))
            }
        }
    }

    fun buscaDadosVacina(id: Int) {
        viewModelScope.launch {
            try {
                val v = vacinasRepository.buscarVacinaPorId(id)
                _vacina.postValue(v)
            } catch (e: Exception) {
                _validacao.value = ValidacaoModel(application.getString(R.string.aviso_erro_acesso_bd))
            }
        }
    }
}