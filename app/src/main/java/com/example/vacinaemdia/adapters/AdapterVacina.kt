package com.example.vacinaemdia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaemdia.R
import com.example.vacinaemdia.databinding.AdapterListaBinding
import com.example.vacinaemdia.model.Vacina

class AdapterVacina(lista: ArrayList<Vacina>, val listenerAdapter: ClickItemVacinaListener): RecyclerView.Adapter<AdapterVacina.ViewHolderVacina>() {

    private var listaVacinas: ArrayList<Vacina> = lista

    inner class ViewHolderVacina(binding: AdapterListaBinding, val listenerVH: ClickItemVacinaListener): RecyclerView.ViewHolder(binding.root){
        var nomeVacina: TextView
        var statusVacina: TextView
        init {
            nomeVacina = binding.textNome
            statusVacina = binding.textStatus

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
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_lista, parent, false)

        val binding = AdapterListaBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderVacina(binding, listenerAdapter)
    }

    override fun onBindViewHolder(holder: ViewHolderVacina, position: Int) {
        var v = listaVacinas[position]
        holder.nomeVacina.setText(v.nomeVacina)
        if (v.statusVacina){
            holder.statusVacina.text = "Vacinado em ${v.dataUltimaDose}"
        }else{
            holder.statusVacina.text = "Vacinação Pendente"
        }
    }

    override fun getItemCount(): Int {
        return listaVacinas.size
    }
}