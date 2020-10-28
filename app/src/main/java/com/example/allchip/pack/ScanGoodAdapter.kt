package com.example.allchip.pack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allchip.R
import com.example.allchip.data.model.Good


class ScanGoodAdapter(var list: List<Good>) : RecyclerView.Adapter<ScanGoodAdapter.GoodViewHolder>() {
    var mClickListener: ((view: View, position: Int) -> Unit?)? = null

    inner class GoodViewHolder(var item:View) : RecyclerView.ViewHolder(item){
        val index:TextView = item.findViewById(R.id.index)
        val type:TextView = item.findViewById(R.id.type)
        val number: TextView = item.findViewById(R.id.number)
        val delete:Button = item.findViewById(R.id.btn_delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodViewHolder {
        return GoodViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_scan_good, parent , false ))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GoodViewHolder, position: Int) {
        val item = list[position]
        holder.index.text = "${item.item_index}"
        holder.type.text = "型号:${item.type}"
        holder.number.text = "${item.count}"
        holder.itemView.setOnClickListener {
            mClickListener?.invoke(it , position)
        }
        holder.delete.setOnClickListener {
            mClickListener?.invoke(it , position)
        }
    }

    fun setOnItemClickListener(listener: (View, Int) -> Unit) {
        this.mClickListener = listener
    }
}