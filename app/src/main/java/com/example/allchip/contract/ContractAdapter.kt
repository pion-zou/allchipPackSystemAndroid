package com.example.allchip.contract

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allchip.R
import com.example.allchip.data.model.Contract


class ContractAdapter(var list: List<Contract>) : RecyclerView.Adapter<ContractAdapter.ContractViewHolder>() {
    var mClickListener: ((view: View, position: Int) -> Unit?)? = null

    inner class ContractViewHolder(var item:View) : RecyclerView.ViewHolder(item){
        val name:TextView = item.findViewById(R.id.name)
        val creator:TextView = item.findViewById(R.id.creator)
        val time: TextView = item.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractViewHolder {
        return ContractViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_contract, parent , false ))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ContractViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = "合同号:${item.number}"
        holder.creator.text = "创建人:${item.creator}"
        holder.time.text = "创建时间:${item.create_time}"
        holder.itemView.setOnClickListener {
            mClickListener?.invoke(holder.itemView , position)
        }
    }

    public fun setOnItemClickListener(listener: (View, Int) -> Unit) {
        this.mClickListener = listener
    }
}