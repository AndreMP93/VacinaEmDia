package com.example.vacinaemdia.activity

import android.content.ContentValues
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
private lateinit var boxInformacoes: EditText
private lateinit var boxDataVacinacao: EditText


private lateinit var bancoDados: BancoDadosHelper

class AdicionarVacinaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_vacina)

        inicializarVariaveis()
        bancoDados = BancoDadosHelper(applicationContext)
        val cv: ContentValues = ContentValues()

        botaoSalvar.setOnClickListener {
            if(boxNomeVacina.text.toString() == ""){
                println("TESTE: NOME EM BRANCO")
                boxNomeVacina.hint = "Campo Obrigatorio"
                boxNomeVacina.setHintTextColor(ContextCompat.getColor(applicationContext, R.color.vermelho))
            }else{
                val status = if (switchStatus.isChecked) 1 else 0
                val dosesRecebidas = if(boxDosesRecebidas.text.toString() == "") 0 else boxDosesRecebidas.text.toString().toInt()

                val v = Vacina(1,
                    boxNomeVacina.text.toString(),
                    status,
                    dosesRecebidas,
                    boxDataVacinacao.text.toString(),
                    boxInformacoes.text.toString())

                val db = BancoDadosHelper(applicationContext)
                try {
                    println("Teste:* status = $status")
                    println("Teste: **" + db.adicionarVacina(v).toString())
                }catch (e: Exception){
                    Log.i("TESTE:", "ERRO AO ADICIONAR VACINA")

                }

                limparCampos()
            }


        }
    }

    fun inicializarVariaveis(){
        botaoSalvar = findViewById(R.id.buttonFormularioSalvar)
        boxNomeVacina = findViewById(R.id.boxNomeVacina)
        switchStatus = findViewById(R.id.switchStatusVacina)
        boxDataVacinacao = findViewById(R.id.boxDataUltVacinacao)
        boxDosesRecebidas = findViewById(R.id.boxDosesRecebidas)
        boxInformacoes = findViewById(R.id.boxInformacoes)
    }

    fun limparCampos(){
        boxNomeVacina.setText("")
        boxDataVacinacao.setText("")
        boxDosesRecebidas.setText("")
        boxInformacoes.setText("")
        switchStatus.isChecked = false
    }

}