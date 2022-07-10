package com.example.vacinaemdia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vacinaemdia.database.daos.VacinaDao
import com.example.vacinaemdia.model.Vacina

@Database(entities = [Vacina::class], version = 1)
abstract class VacinaEmDiaDatabase: RoomDatabase() {

    abstract val vacinaDao: VacinaDao

    companion object{
        @Volatile
        private var INSTANCE: VacinaEmDiaDatabase? = null

        fun getInstance(contexto: Context): VacinaEmDiaDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        contexto.applicationContext,
                        VacinaEmDiaDatabase::class.java,
                        "vacina_em_dia_bd"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}