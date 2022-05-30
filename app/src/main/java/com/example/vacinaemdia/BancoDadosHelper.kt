package com.example.vacinaemdia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.vacinaemdia.model.Vacina

class BancoDadosHelper(context: Context?) : SQLiteOpenHelper(context, name, factory, VERSION) {

    companion object{
        val name: String? = "VacinaEmDia"
        val factory: SQLiteDatabase.CursorFactory? = null
        const val VERSION: Int = 1
        const val tabelaVacinas: String = "vacinas"
        const val cID = "id"
        const val cNome = "nome"
        const val cStatus = "status"
        const val cDosesRecebidas = "doses_recebidas"
        const val cDataUltimaDose = "data_ultima_dose"
        const val cIdadeIndicada = "idade_indicada"
        const val cPrevencao = "prevencao"
        const val cDosesNecessarias = "doses_necessarias"
        const val cModoAplicacao = "modo_aplicacao"
    }




    override fun onCreate(db: SQLiteDatabase?) {

        val query = "CREATE TABLE IF NOT EXISTS $tabelaVacinas ($cID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$cNome VARCHAR, " +
                "$cStatus boolean not null, " +
                "$cDosesRecebidas INT (1), " +
                "$cDataUltimaDose date," +
                "$cIdadeIndicada VARCHAR, " +
                "$cPrevencao VARCHAR, " +
                "$cDosesNecessarias INT (1), " +
                "$cModoAplicacao VARCHAR);"
        try {

            db?.execSQL(query)
            Log.i("TESTE:", "Sucessoa ao Instalar APP")

        }catch (e: Exception){
            e.printStackTrace()
            Log.i("TESTE:", "ERRO ao Instalar APP")

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS $tabelaVacinas;"

        try {

            db?.execSQL( query)
            onCreate(db)
            Log.i("TESTE:", "Sucessoa ao Atualizar APP")

        }catch (e: Exception){
            Log.i("TESTE:", "Erro ao Atualizar APP")
        }
    }

    fun adicionarVacina(v: Vacina): Boolean{
        val db = this.writableDatabase
        val valores = ContentValues()
        valores.put(cNome, v.nomeVacina)
        valores.put(cStatus, v.statusVacina)
        valores.put(cDosesRecebidas, v.dosesRecebidasVacina)
        valores.put(cDataUltimaDose, v.dataUltimaDose)
        valores.put(cPrevencao, v.prevencao)
        valores.put(cIdadeIndicada, v.idadeIndicada)
        valores.put(cDosesNecessarias, v.dosesNecessaria)
        valores.put(cModoAplicacao, v.modoAplicacao)

        val _success = db.insert(tabelaVacinas,null,valores)

        return (("$_success").toInt() != -1)
    }

    fun getVacina(id: Int): Vacina {
        val db = writableDatabase
        val query = "SELECT * FROM $tabelaVacinas WHERE $cID = $id"
        val cursor = db.rawQuery(query, null)
        cursor?.moveToFirst()

        val indiceId = cursor.getColumnIndex(cID)
        val indiceNome = cursor.getColumnIndex(cNome)
        val indiceStatus = cursor.getColumnIndex(cStatus)
        val indiceDosesReceb = cursor.getColumnIndex(cDosesRecebidas)
        val indiceData = cursor.getColumnIndex(cDataUltimaDose)
        val indiceIdade = cursor.getColumnIndex(cIdadeIndicada)
        val indicePrevencao = cursor.getColumnIndex(cPrevencao)
        val indiceDosesNeces = cursor.getColumnIndex(cDosesNecessarias)
        val indiceModoAplic = cursor.getColumnIndex(cModoAplicacao)
        cursor.close()

        return Vacina(
            cursor.getInt(indiceId),
            cursor.getString(indiceNome),
            cursor.getInt(indiceStatus),
            cursor.getInt(indiceDosesReceb),
            cursor.getString(indiceData),
            cursor.getString(indiceIdade),
            cursor.getString(indicePrevencao),
            cursor.getInt(indiceDosesNeces),
            cursor.getString(indiceModoAplic)
        )
    }

    fun listarVacinas(v: ArrayList<Vacina>) {

        val bancoDados = writableDatabase
        val query = "SELECT * FROM $tabelaVacinas;"
        val cursor: Cursor = bancoDados.rawQuery(query, null)
        val indiceId = cursor.getColumnIndex(cID)
        val indiceNome = cursor.getColumnIndex("nome")
        val indiceStatus = cursor.getColumnIndex("status")
        val indiceDosesReceb = cursor.getColumnIndex("doses_recebidas")
        val indiceData = cursor.getColumnIndex("data_ultima_dose")
        val indiceIdade = cursor.getColumnIndex("idade_indicada")
        val indicePrevencao = cursor.getColumnIndex("prevencao")
        val indiceDosesNeces = cursor.getColumnIndex("doses_necessarias")
        val indiceModoAplic = cursor.getColumnIndex("modo_aplicacao")

        cursor.moveToFirst()

        while (cursor != null) {
            println("TESTE: XXX.")
            v.add(
                Vacina(
                    cursor.getInt(indiceId),
                    cursor.getString(indiceNome),
                    cursor.getInt(indiceStatus),
                    cursor.getInt(indiceDosesReceb),
                    cursor.getString(indiceData),
                    cursor.getString(indiceIdade),
                    cursor.getString(indicePrevencao),
                    cursor.getInt(indiceDosesNeces),
                    cursor.getString(indiceModoAplic)
                )
            )
            cursor.moveToNext()
        }
    }

    fun updateVacinas(v: Vacina): Boolean{
        val db = this.writableDatabase
        val valores = ContentValues()
        valores.put(cNome, v.nomeVacina)
        valores.put(cStatus, v.statusVacina)
        valores.put(cDosesRecebidas, v.dosesRecebidasVacina)
        valores.put(cDataUltimaDose, v.dataUltimaDose)
        valores.put(cPrevencao, v.prevencao)
        valores.put(cIdadeIndicada, v.idadeIndicada)
        valores.put(cDosesNecessarias, v.dosesNecessaria)
        valores.put(cModoAplicacao, v.modoAplicacao)

        val _success = db.update(tabelaVacinas, valores, cID + "=?", arrayOf(v.idVacina.toString())).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    fun deletarVacina(id: Int): Boolean{

        val db = this.writableDatabase
        val _success = db.delete(tabelaVacinas, cID + "=?", arrayOf(id.toString())).toLong()
        return ("$_success").toInt() != -1

    }
}