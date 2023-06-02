package com.example.vacinaemdia.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaemdia.adapters.AdapterVacina
import com.example.vacinaemdia.adapters.ClickItemVacinaListener
import com.example.vacinaemdia.database.VacinaEmDiaDatabase
import com.example.vacinaemdia.database.repository.VacinasRepository
import com.example.vacinaemdia.databinding.ActivityMainBinding
import com.example.vacinaemdia.model.Vacina
import com.example.vacinaemdia.viewmodel.VacinaViewModel
import com.example.vacinaemdia.viewmodel.VacinaViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ClickItemVacinaListener {

    private lateinit var viewModel: VacinaViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instanciarViewModel()

        binding.fabAddVacina.setOnClickListener{
            startActivity(Intent(applicationContext, FormularioVacinaActivity::class.java))
        }
    }

    override fun onStart(){
        super.onStart()
        recuperarVacinas()
        setObserver()
    }

    override fun onItemClickListener(vacina: Vacina) {
        val intent = Intent(applicationContext, DetalhesVacinaActivity::class.java)
        intent.putExtra("idVacina", vacina.id)
        startActivity(intent)
    }

    override fun onItemLongClickListener(vacina: Vacina){
        println("TESTE: ${vacina.dataUltimaDose} onItemLongClickListener()")
    }

    private fun instanciarViewModel(){
        val bd = VacinaEmDiaDatabase.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            VacinaViewModelFactory(application, VacinasRepository(bd.vacinaDao))
        )[VacinaViewModel::class.java]
    }

    private fun recuperarVacinas(){
        viewModel.recuperarListaDeVacinas()
    }

    private fun configurarRecycleView(listaVacinas: ArrayList<Vacina>){
        recyclerView = binding.recyclerViewVacinas
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayout.VERTICAL))
        recyclerView.adapter = AdapterVacina(listaVacinas, this)
    }

    private fun exibirSnackbar(menssagem: String){
        Snackbar.make(
            binding.root,
            menssagem,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun setObserver(){
        viewModel.listaDeVacinas.observe(this) {
            configurarRecycleView(it)
        }

        viewModel.validacao.observe(this){
            exibirSnackbar(it.errorMessage())
        }
//
//        viewModel.erroManager.observe(this, Observer {
//            exibirSnackbar(getString(it))
//        })
    }
}