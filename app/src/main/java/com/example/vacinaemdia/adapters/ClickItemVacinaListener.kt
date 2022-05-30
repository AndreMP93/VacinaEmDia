package com.example.vacinaemdia.adapters

import com.example.vacinaemdia.model.Vacina

interface ClickItemVacinaListener {

    fun onItemClickListener(vacina: Vacina)

    fun onItemLongClickListener(vacina: Vacina)
}