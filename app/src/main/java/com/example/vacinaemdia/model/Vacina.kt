package com.example.vacinaemdia.model

import java.io.Serializable

class Vacina(var idVacina: Int,
             var nomeVacina: String?,
             var statusVacina: Int?,
             var dosesRecebidasVacina: Int,
             var dataUltimaDose: String,
             var informacoes: String): Serializable{

}