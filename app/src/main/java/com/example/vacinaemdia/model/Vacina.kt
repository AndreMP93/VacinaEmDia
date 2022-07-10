package com.example.vacinaemdia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "vacinas")
class Vacina(
    @ColumnInfo(name= "nome") var nomeVacina: String,
    @ColumnInfo(name= "status") var statusVacina: Boolean,
    @ColumnInfo(name= "doses_recebidas") var dosesRecebidasVacina: Int,
    @ColumnInfo(name= "data_ultima_dose") var dataUltimaDose: String,
    @ColumnInfo(name= "informacoes") var informacoes: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
): Serializable{

}