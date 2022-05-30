package com.example.vacinaemdia.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.core.content.ContextCompat
import com.example.vacinaemdia.BancoDadosHelper
import com.example.vacinaemdia.R
import com.example.vacinaemdia.model.Vacina

private lateinit var botaoSalvar: Button
private lateinit var boxNomeVacina: EditText
private lateinit var switchStatus: Switch
private lateinit var boxDosesRecebidas: EditText
private lateinit var boxPrevencao: EditText
private lateinit var boxIdadeRecomendada: EditText
private lateinit var boxDosesNecessarias: EditText
private lateinit var boxDataVacinacao: EditText
private lateinit var boxModoAplicacao: EditText
private lateinit var vacina: Vacina

class EditarVacinaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_vacina)

        //Recuperando dados do DetalhesVacinaACtivity
        val dados: Bundle? = intent.extras
        vacina = dados?.getSerializable("objetoVacina") as Vacina
        println("TESTE: EDITAR")
        inicializarVariaveis(vacina)

        //val bancoDados = BancoDadosHelper(applicationContext)
        //val cv: ContentValues = ContentValues()

        botaoSalvar.setOnClickListener {

            if(boxNomeVacina.text.toString() == ""){
                println("TESTE: NOME EM BRANCO")
                boxNomeVacina.hint = "Campo Obrigatorio"
                boxNomeVacina.setHintTextColor(ContextCompat.getColor(applicationContext, R.color.vermelho))
            }else{
                val status = if (switchStatus.isChecked) 1 else 0
                val dosesRecebidas = if(boxDosesRecebidas.text.toString() == "") 0 else boxDosesRecebidas.text.toString().toInt()
                val dosesNecessarias = if(boxDosesNecessarias.text.toString() == "") 0 else boxDosesNecessarias.text.toString().toInt()

                val v = Vacina(
                    vacina.idVacina,
                    boxNomeVacina.text.toString(),
                    status,
                    dosesRecebidas,
                    boxDataVacinacao.text.toString(),
                    boxIdadeRecomendada.text.toString(),
                    boxPrevencao.text.toString(),
                    dosesNecessarias,
                    boxModoAplicacao.text.toString())

                val db = BancoDadosHelper(applicationContext)
                try {
                    println("Teste: ** ${db.updateVacinas(v)}")
                }catch (e: Exception){
                    Log.i("TESTE:", "ERRO AO ATUALIZAR VACINA")

                }
                val intent = Intent(applicationContext, DetalhesVacinaActivity::class.java)
                intent.putExtra("objetoVacina", v)
                startActivity(intent)
            }

        }


    }

    fun inicializarVariaveis(v: Vacina){
        botaoSalvar = findViewById(R.id.buttonFormularioSalvar)
        boxNomeVacina = findViewById(R.id.boxNomeVacina)
        switchStatus = findViewById(R.id.switchStatusVacina)
        boxDataVacinacao = findViewById(R.id.boxDataUltVacinacao)
        boxDosesRecebidas = findViewById(R.id.boxDosesRecebidas)
        boxPrevencao = findViewById(R.id.boxPrevencao)
        boxDosesNecessarias = findViewById(R.id.boxDosesNecessarias)
        boxIdadeRecomendada = findViewById(R.id.boxIdade)
        boxModoAplicacao = findViewById(R.id.boxModoAplicacao)

        boxNomeVacina.setText(v.nomeVacina)
        switchStatus.isChecked = v.statusVacina == 1
        boxDataVacinacao.setText(v.dataUltimaDose)
        boxDosesRecebidas.setText(v.dosesRecebidasVacina.toString())
        boxPrevencao.setText(v.prevencao)
        boxDosesNecessarias.setText(v.dosesNecessaria.toString())
        boxIdadeRecomendada.setText(v.idadeIndicada)
        boxModoAplicacao.setText(v.modoAplicacao)
    }
}


