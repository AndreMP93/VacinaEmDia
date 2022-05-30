package com.example.vacinaemdia.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaemdia.BancoDadosHelper
import com.example.vacinaemdia.R
import com.example.vacinaemdia.adapters.AdapterVacina
import com.example.vacinaemdia.adapters.ClickItemVacinaListener
import com.example.vacinaemdia.model.Vacina
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ClickItemVacinaListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddVacian: FloatingActionButton
    private lateinit var bancoDados: BancoDadosHelper
    private var listaVacinas = ArrayList<Vacina>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAddVacian = findViewById(R.id.fabAddVacina)
        fabAddVacian.setOnClickListener {
            startActivity(Intent(applicationContext, AdicionarVacinaActivity::class.java))
            println("Teste: 0")
        }

        //Configurando Banco de Dados
        bancoDados = BancoDadosHelper(applicationContext)

        //Carregando Dados da Tabela Vacinas
        try {
            bancoDados.listarVacinas(listaVacinas)
        }catch (e: Exception){
            Log.i("TESTE: ", "ERRO AO CARREGAR TABELA")
        }

        //Configurando o Adapter
        val adaptador = AdapterVacina(listaVacinas, this)

        //Configurando o RecycleView
        recyclerView = findViewById(R.id.recyclerViewVacinas)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayout.VERTICAL))
        recyclerView.adapter = adaptador

    }


    override fun onItemClickListener(vacina: Vacina) {
        println("RESULTADO TESTE: ${vacina.nomeVacina}")

        val intent = Intent(applicationContext, DetalhesVacinaActivity::class.java)
        intent.putExtra("objetoVacina", vacina)
        Toast.makeText(applicationContext, "TESTE: ${vacina.nomeVacina}", Toast.LENGTH_LONG).show()
        startActivity(intent)
    }

    override fun onItemLongClickListener(vacina: Vacina) {
        println("RESULTADO TESTE: ${vacina.dataUltimaDose}")
    }


}