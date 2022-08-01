package com.example.vacinaemdia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vacinaemdia.R
import com.example.vacinaemdia.database.VacinaEmDiaDatabase
import com.example.vacinaemdia.database.repository.VacinasRepository
import com.example.vacinaemdia.databinding.ActivityAdicionarVacinaBinding
import com.example.vacinaemdia.model.Vacina
import com.example.vacinaemdia.viewmodel.VacinaViewModel
import com.example.vacinaemdia.viewmodel.VacinaViewModelFactory
import com.google.android.material.snackbar.Snackbar


class AdicionarVacinaActivity : AppCompatActivity() {

    private lateinit var viewModel: VacinaViewModel
    private lateinit var binding: ActivityAdicionarVacinaBinding

    private lateinit var botaoSalvar: Button
    private lateinit var boxNomeVacina: EditText
    private lateinit var switchStatus: Switch
    private lateinit var boxDosesRecebidas: EditText
    private lateinit var boxInformacoes: EditText
    private lateinit var boxDataVacinacao: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAdicionarVacinaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        instanciarViewModel()
        inicializarVariaveis()

        botaoSalvar.setOnClickListener {
            salvarVacinaBancoDados()
        }

        setObserver()

    }



    private fun inicializarVariaveis(){
        botaoSalvar = binding.formularioVacina.buttonFormularioSalvar
        boxNomeVacina = binding.formularioVacina.boxNomeVacina
        switchStatus = binding.formularioVacina.switchStatusVacina
        boxDataVacinacao = binding.formularioVacina.boxDataUltVacinacao
        boxDosesRecebidas = binding.formularioVacina.boxDosesRecebidas
        boxInformacoes = binding.formularioVacina.boxInformacoes
    }

    private fun limparCampos(){
        boxNomeVacina.setText("")
        boxDataVacinacao.setText("")
        boxDosesRecebidas.setText("")
        boxInformacoes.setText("")
        switchStatus.isChecked = false
    }

    private fun salvarVacinaBancoDados(){
        val dosesRecebidas = if(boxDosesRecebidas.text.toString().isEmpty() || boxDosesRecebidas.text.toString().isBlank()) 0
                            else boxDosesRecebidas.text.toString().toInt()
        val v = Vacina(
            boxNomeVacina.text.toString(),
            switchStatus.isChecked,
            dosesRecebidas,
            boxDataVacinacao.text.toString(),
            boxInformacoes.text.toString()
        )

        viewModel.cadastrarNovaVacina(v)
    }

    private fun exibirSnackbar(menssagem: String){
        Snackbar.make(
            binding.root,
            menssagem,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun instanciarViewModel(){
        val bd = VacinaEmDiaDatabase.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            VacinaViewModelFactory(VacinasRepository(bd.vacinaDao))
        ).get(VacinaViewModel::class.java)
    }

    private fun setObserver() {
        viewModel.erroManager.observe(this, Observer {
            exibirSnackbar(it)
        })

        viewModel.successManager.observe(this, Observer {
            if (it){
                limparCampos()
                finish()
            }
        })
    }

}