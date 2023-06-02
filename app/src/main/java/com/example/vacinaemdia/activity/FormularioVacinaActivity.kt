package com.example.vacinaemdia.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider

import com.example.vacinaemdia.R;
import com.example.vacinaemdia.database.VacinaEmDiaDatabase
import com.example.vacinaemdia.database.repository.VacinasRepository
import com.example.vacinaemdia.databinding.ActivityFormularioVacinaBinding;
import com.example.vacinaemdia.model.Vacina
import com.example.vacinaemdia.viewmodel.VacinaViewModel
import com.example.vacinaemdia.viewmodel.VacinaViewModelFactory
import com.google.android.material.snackbar.Snackbar

public class FormularioVacinaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormularioVacinaBinding
    private lateinit var viewModel: VacinaViewModel
    private var vacina: Vacina? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormularioVacinaBinding.inflate(layoutInflater)
        setContentView(binding.root);

        viewModel = ViewModelProvider(
            this,
            VacinaViewModelFactory(
                application,
                VacinasRepository(VacinaEmDiaDatabase.getInstance(this).vacinaDao)
            )
        )[VacinaViewModel::class.java]

        val dados = intent.extras
        vacina = dados?.getSerializable("objetoVacina") as Vacina?
        println("TESTE: vacina = ${vacina.toString()}")
        if(vacina != null){
            binding.editNomeVacina.setText(vacina!!.nomeVacina)
            binding.switchVacinaStatus.isChecked = vacina!!.statusVacina
            binding.editDataUltVacinacao.setText(vacina!!.dataUltimaDose)
            binding.editDosesRecebidas.setText(vacina!!.dosesRecebidasVacina.toString())
            binding.editInformacoesVacina.setText(vacina!!.informacoes)
            binding.buttonFormularioSalvarAutualizar.text = getString(R.string.botao_editar)
        }

        binding.buttonFormularioSalvarAutualizar.setOnClickListener {
            val newVacina = Vacina(
                binding.editNomeVacina.text.toString(),
                binding.switchVacinaStatus.isChecked,
                binding.editDosesRecebidas.text.toString().toInt(),
                binding.editDataUltVacinacao.text.toString(),
                binding.editInformacoesVacina.text.toString()
            )
            println("TESTE 2: vacina = ${vacina.toString()}")
            if(vacina != null){
                println("Teste: newVacina = $newVacina")
                viewModel.atualizarDadosVacina(newVacina)
            }else{
                viewModel.cadastrarNovaVacina(newVacina)
            }
        }
        setObserves()
    }

    private fun setObserves(){
        viewModel.validacao.observe(this){
            if(it.status()){
                exibirSnackbar(getString(R.string.aviso_dados_salvos))
                finish()
            }else{
                exibirSnackbar(it.errorMessage())
            }
        }
    }

    private fun exibirSnackbar(menssagem: String){
        Snackbar.make(
            binding.root,
            menssagem,
            Snackbar.LENGTH_LONG
        ).show()
    }
}