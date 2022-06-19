package com.example.vacinaemdia.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.vacinaemdia.BancoDadosHelper
import com.example.vacinaemdia.R
import com.example.vacinaemdia.model.Vacina

class DetalhesVacinaActivity : AppCompatActivity() {

    private lateinit var vacina: Vacina
    private lateinit var nomeVacina: TextView
    private lateinit var detalheStatus: TextView
    private lateinit var detalheInformacoes: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_vacina)

        inicializarVariaveis()
        setSupportActionBar(findViewById(R.id.toolbar_Detalhes))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Recuperando dados do MainActivity
        val dados: Bundle? = intent.extras

        if(dados != null){
            vacina = dados.getSerializable("objetoVacina") as Vacina
        }


    }

    @SuppressLint("SetTextI18n")
    override fun onStart(){
        super.onStart()
        //Exibindo dados na Tela
        val bancoDados = BancoDadosHelper(applicationContext)
        vacina = bancoDados.getVacina(vacina.idVacina)
        nomeVacina.text = vacina.nomeVacina
        if(vacina.statusVacina == 1){
            detalheStatus.setTextColor(ContextCompat.getColor(applicationContext, R.color.verde))
            detalheStatus.text  = getString(R.string.statusData) + " ${vacina.dataUltimaDose}.\n" +
                    getString(R.string.statusDoses) + " ${vacina.dosesRecebidasVacina}"
        }else{
            detalheStatus.text = getString(R.string.statusVacinaPendente)
        }
        detalheInformacoes.text = vacina.informacoes

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
        val db = BancoDadosHelper(applicationContext)
        db.deletarVacina(v.idVacina)
        finish()
    }

    fun editarVacina(v: Vacina){
        val intent = Intent(applicationContext, EditarVacinaActivity::class.java)
        intent.putExtra("objetoVacina", v)
        startActivity(intent)
    }

}