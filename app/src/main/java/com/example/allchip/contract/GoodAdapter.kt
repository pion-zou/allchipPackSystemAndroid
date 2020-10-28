package com.example.allchip.contract

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allchip.R
import com.example.allchip.data.model.Contract
import com.example.allchip.data.model.Good


class GoodAdapter(var list: List<Good>) : RecyclerView.Adapter<GoodAdapter.GoodViewHolder>() {
    var mClickListener: ((view: View, position: Int) -> Unit?)? = null

    inner class GoodViewHolder(var item:View) : RecyclerView.ViewHolder(item){
        val index:TextView = item.findViewById(R.id.index)
        val type:TextView = item.findViewById(R.id.type)
        val manufacturer: TextView = item.findViewById(R.id.manufacturer)
        val package_name: TextView = item.findViewById(R.id.package_name)
        val number: TextView = item.findViewById(R.id.number)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodViewHolder {
        return GoodViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_good, parent , false ))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GoodViewHolder, position: Int) {
        val item = list[position]
        holder.index.text = "${item.item_index}"
        holder.type.text = "型号:${item.type}"
        holder.manufacturer.text = "生产商:${item.manufacturer}"
        holder.package_name.text = "  /${item.`package`}"
        holder.number.text = "${item.package_count} / ${item.count}"
        holder.itemView.setOnClickListener {
            mClickListener?.invoke(holder.itemView , position)
        }
    }

    public fun setOnItemClickListener(listener: (View, Int) -> Unit) {
        this.mClickListener = listener
    }
}