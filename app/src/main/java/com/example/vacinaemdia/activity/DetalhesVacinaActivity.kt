package com.example.vacinaemdia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.vacinaemdia.BancoDadosHelper
import com.example.vacinaemdia.R
import com.example.vacinaemdia.model.Vacina

class DetalhesVacinaActivity : AppCompatActivity() {

    private lateinit var vacina: Vacina
    private lateinit var nomeVacina: TextView
    private lateinit var detalheStatus: TextView
    private lateinit var detalhePrevencao: TextView
    private lateinit var detalheIdade: TextView
    private lateinit var detalheDosesNecessaria: TextView
    private lateinit var detalheModoAplicacao: TextView
    private lateinit var botaoExcluir: Button
    private lateinit var botaoEditarVacina: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_vacina)

        inicializarVariaveis()

        //Recuperando dados do MainActivity
        val dados: Bundle? = intent.extras
        vacina = dados?.getSerializable("objetoVacina") as Vacina

        //Exibindo dados na Tela
        nomeVacina.text = vacina.nomeVacina
        if(vacina.statusVacina == 1){
            detalheStatus.setTextColor(ContextCompat.getColor(applicationContext, R.color.verde))
            "Vacinado em ${vacina.dataUltimaDose}.\n Doses da Vacina Recebidas: ${vacina.dosesRecebidasVacina}".also { detalheStatus.text = it }
        }else{
            detalheStatus.text = "Vacina Pendente"
        }
        detalhePrevencao.text = vacina.prevencao
        detalheIdade.text = vacina.idadeIndicada
        detalheDosesNecessaria.text = "Doses necessarias: ${vacina.dosesNecessaria}"
        detalheModoAplicacao.text = vacina.modoAplicacao

        //Configurando Botão Excluir Vacina
        botaoExcluir.setOnClickListener {
            excluirVacina(vacina)
        }

        //Configurando Botão Editar Vacina
        botaoEditarVacina.setOnClickListener {
            editarVacina(vacina)
        }



    }

    fun inicializarVariaveis(){
        nomeVacina = findViewById(R.id.textViewNomeVacina)
        detalheStatus = findViewById(R.id.detalheStatusVacina)
        detalhePrevencao = findViewById(R.id.detalhePrevencao)
        detalheIdade = findViewById(R.id.detalheIdade)
        detalheDosesNecessaria = findViewById(R.id.detalheDosesNecessarias)
        detalheModoAplicacao = findViewById(R.id.detalheModoAplicacao)
        botaoExcluir = findViewById(R.id.buttonExcluirVacina)
        botaoEditarVacina = findViewById(R.id.buttonEditarVacina)

    }

    fun excluirVacina(v: Vacina){
        val db = BancoDadosHelper(applicationContext)
        println("TESTE: ${db.deletarVacina(v.idVacina)}")
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    fun editarVacina(v: Vacina){
        val intent = Intent(applicationContext, EditarVacinaActivity::class.java)
        intent.putExtra("objetoVacina", v)
        startActivity(intent)
    }

}