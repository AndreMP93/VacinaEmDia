package com.example.vacinaemdia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaemdia.R
import com.example.vacinaemdia.model.Vacina

class AdapterVacina(lista: ArrayList<Vacina>, val listenerAdapter: ClickItemVacinaListener): RecyclerView.Adapter<AdapterVacina.ViewHolderVacina>() {

    private var listaVacinas: ArrayList<Vacina> = lista

    inner class ViewHolderVacina(itemView: View, val listenerVH: ClickItemVacinaListener): RecyclerView.ViewHolder(itemView){
        var nomeVacina: TextView
        var statusVacina: TextView
        init {
            nomeVacina = itemView.findViewById(R.id.textNome)
            statusVacina = itemView.findViewById(R.id.textStatus)

            itemView.setOnClickListener {
                listenerVH.onItemClickListener(listaVacinas[adapterPosition])

            itemView.setOnLongClickListener {
                listenerVH.onItemLongClickListener(listaVacinas[adapterPosition])
                true
            }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVacina {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_lista, parent, false)
        return ViewHolderVacina(view, listenerAdapter)
    }

    override fun onBindViewHolder(holder: ViewHolderVacina, position: Int) {
        var v = listaVacinas[position]
        holder.nomeVacina.setText(v.nomeVacina)
        if (v.statusVacina == 1){
            holder.statusVacina.text = "Vacinado em ${v.dataUltimaDose}"
        }else{
            holder.statusVacina.text = "Vacinação Pendente"
        }
    }

    override fun getItemCount(): Int {
        return listaVacinas.size
    }
}