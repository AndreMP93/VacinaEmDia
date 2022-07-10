package com.example.vacinaemdia.database.repository

import com.example.vacinaemdia.database.daos.VacinaDao
import com.example.vacinaemdia.model.Vacina

class VacinasRepository(private val vacinaDao: VacinaDao) {

    suspend fun cadastrarVacina(vacina: Vacina) = vacinaDao.cadastrarVacina(vacina)

    suspend fun excluirVacina(vacina: Vacina) = vacinaDao.excluirVacina(vacina)

    suspend fun atualizarVacina(vacina: Vacina) = vacinaDao.atualizarVacina(vacina)

    suspend fun listarTodaAsVacinas() = vacinaDao.listarTodaAsVacinas()

    suspend fun buscarVacinaPorId(vId: Int) = vacinaDao.buscarVacinaPorId(vId)

}