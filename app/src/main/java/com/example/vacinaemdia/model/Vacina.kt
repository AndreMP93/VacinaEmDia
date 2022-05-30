package com.example.vacinaemdia.model

import java.io.Serializable

class Vacina(var idVacina: Int,
             var nomeVacina: String?,
             var statusVacina: Int?,
             var dosesRecebidasVacina: Int,
             var dataUltimaDose: String,
             var idadeIndicada: String,
             var prevencao: String,
             var dosesNecessaria: Int,
             var modoAplicacao: String): Serializable{

}