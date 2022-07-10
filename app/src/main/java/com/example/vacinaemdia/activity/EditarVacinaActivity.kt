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
import com.example.vacinaemdia.databinding.ActivityEditarVacinaBinding
import com.example.vacinaemdia.model.Vacina
import com.example.vacinaemdia.viewmodel.VacinaViewModel
import com.example.vacinaemdia.viewmodel.VacinaViewModelFactory
import com.google.android.material.snackbar.Snackbar


class EditarVacinaActivity : AppCompatActivity() {

    private lateinit var viewModel: VacinaViewModel
    private lateinit var binding: ActivityEditarVacinaBinding

    private lateinit var botaoSalvar: Button
    private lateinit var boxNomeVacina: EditText
    private lateinit var switchStatus: Switch
    private lateinit var boxDosesRecebidas: EditText
    private lateinit var boxDataVacinacao: EditText
    private lateinit var boxInformacoes: EditText
    private lateinit var vacina: Vacina

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarVacinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instanciarViewModel()

        //Recuperando dados do DetalhesVacinaACtivity
        val dados: Bundle? = intent.extras
        vacina = dados?.getSerializable("objetoVacina") as Vacina
        inicializarVariaveis(vacina)

        viewModel.erroManager.observe(this, Observer {
            exibirSnackbar(it)
        })

        botaoSalvar.setOnClickListener {
            if(boxNomeVacina.text.toString().isEmpty() || boxNomeVacina.text.toString().isBlank()){
                boxNomeVacina.hint = getString(R.string.aviso_campo_obrigatorio)
                boxNomeVacina.setHintTextColor(ContextCompat.getColor(applicationContext, R.color.vermelho))
            }else{
                atualizarDadosVacina()
                finish()
            }
        }
    }

    fun inicializarVariaveis(v: Vacina){
        botaoSalvar = binding.formularioVacina.buttonFormularioSalvar
        boxNomeVacina = binding.formularioVacina.boxNomeVacina
        switchStatus = binding.formularioVacina.switchStatusVacina
        boxDataVacinacao = binding.formularioVacina.boxDataUltVacinacao
        boxDosesRecebidas = binding.formularioVacina.boxDosesRecebidas
        boxInformacoes = binding.formularioVacina.boxInformacoes

        boxNomeVacina.setText(v.nomeVacina)
        switchStatus.isChecked = v.statusVacina
        boxDataVacinacao.setText(v.dataUltimaDose)
        boxDosesRecebidas.setText(v.dosesRecebidasVacina.toString())
        boxInformacoes.setText(v.informacoes)
    }

    private fun instanciarViewModel(){
        val bd = VacinaEmDiaDatabase.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            VacinaViewModelFactory(VacinasRepository(bd.vacinaDao))
        ).get(VacinaViewModel::class.java)
    }

    private fun exibirSnackbar(menssagem: String){
        Snackbar.make(
            binding.root,
            menssagem,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun atualizarDadosVacina(){
        val dosesRecebidas = if(boxDosesRecebidas.text.toString().isEmpty() || boxDosesRecebidas.text.toString().isBlank()) 0 else boxDosesRecebidas.text.toString().toInt()
        val v = Vacina(
            boxNomeVacina.text.toString(),
            switchStatus.isChecked,
            dosesRecebidas,
            boxDataVacinacao.text.toString(),
            boxInformacoes.text.toString(),
            vacina.id
        )
        viewModel.atualizarDadosVacina(v)
    }

}


