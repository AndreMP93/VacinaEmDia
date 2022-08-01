package com.example.vacinaemdia.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vacinaemdia.R
import com.example.vacinaemdia.database.VacinaEmDiaDatabase
import com.example.vacinaemdia.database.repository.VacinasRepository
import com.example.vacinaemdia.databinding.ActivityDetalhesVacinaBinding
import com.example.vacinaemdia.model.Vacina
import com.example.vacinaemdia.viewmodel.VacinaViewModel
import com.example.vacinaemdia.viewmodel.VacinaViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class DetalhesVacinaActivity : AppCompatActivity() {

    private lateinit var viewModel: VacinaViewModel
    private lateinit var binding: ActivityDetalhesVacinaBinding

    private var vacina =  Vacina(" ", true, 0, "", "", 1)
    private var idVacina by Delegates.notNull<Int>()
    private lateinit var nomeVacina: TextView
    private lateinit var detalheStatus: TextView
    private lateinit var detalheInformacoes: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetalhesVacinaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        instanciarViewModel()
        inicializarVariaveis()

        setSupportActionBar(binding.toolbarDetalhes)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Recuperando dados do MainActivity
        val dados: Bundle? = intent.extras
        idVacina = dados?.getInt("idVacina")!!
    }


    override fun onStart(){
        super.onStart()
        buscarDadosVacina(idVacina)
        setObserver()
    }

    @SuppressLint("SetTextI18n")
    private fun exibirDadosVacina(){
        nomeVacina.text = vacina.nomeVacina
        if(vacina.statusVacina){
            detalheStatus.setTextColor(ContextCompat.getColor(applicationContext, R.color.verde))
            detalheStatus.text  =  "${getString(R.string.statusData)} ${vacina.dataUltimaDose}.\n" +
                    "${getString(R.string.statusDoses)} ${vacina.dosesRecebidasVacina}"
        }else{
            detalheStatus.text = getString(R.string.statusVacinaPendente)
        }
        detalheInformacoes.text = vacina.informacoes
    }

    private fun buscarDadosVacina(id: Int){
        viewModel.buscaDadosVacina(id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recursos_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.editarVacina-> {editarVacina(vacina)}
            R.id.excluirVacina -> {excluirVacina(vacina)}
        }
        return super.onOptionsItemSelected(item)
    }

    fun inicializarVariaveis(){
        nomeVacina = findViewById(R.id.textViewNomeVacina)
        detalheStatus = findViewById(R.id.detalheStatusVacina)
        detalheInformacoes = findViewById(R.id.detalheInformacoes)
    }
    fun excluirVacina(v: Vacina){
        viewModel.excluirVacina(vacina)
        finish()
    }

    fun editarVacina(v: Vacina){
        val intent = Intent(applicationContext, EditarVacinaActivity::class.java)
        intent.putExtra("objetoVacina", v)
        startActivity(intent)
    }

    private fun instanciarViewModel(){
        val bd = VacinaEmDiaDatabase.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            VacinaViewModelFactory(VacinasRepository(bd.vacinaDao))
        ).get(VacinaViewModel::class.java)
    }

    private fun setObserver(){
        viewModel.vacina.observe(this, Observer {
            vacina = it
            exibirDadosVacina()
        })

        viewModel.erroManager.observe(this, Observer {
            exibirSnackbar(it)
        })
    }

    private fun exibirSnackbar(menssagem: String){
        Snackbar.make(
            binding.root,
            menssagem,
            Snackbar.LENGTH_LONG
        ).show()
    }
}