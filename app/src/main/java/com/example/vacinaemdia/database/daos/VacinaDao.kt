package com.example.vacinaemdia.database.daos

import androidx.room.*
import com.example.vacinaemdia.model.Vacina

@Dao
interface VacinaDao {

    @Insert
    suspend fun cadastrarVacina(vacina: Vacina)

    @Delete
    suspend fun excluirVacina(vacina: Vacina)

    @Update
    suspend fun atualizarVacina(vacina: Vacina)

    @Query("SELECT * FROM vacinas;")
    suspend fun listarTodaAsVacinas(): List<Vacina>

    @Query("SELECT * FROM vacinas WHERE id = :vId;")
    suspend fun buscarVacinaPorId(vId: Int): Vacina
}