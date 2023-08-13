package com.space.aviatory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.space.aviatory.databinding.ListItemBinding

class ScoreAdapter(var ctx: Context): RecyclerView.Adapter<ScoreAdapter.Companion.MyHolder>() {

    var data = mutableListOf<Int>()
    init {
        data = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE).getStringSet("records",HashSet<String>())!!.map { it.toInt() }.toMutableList()
        data.sortBy { -it }
    }

    companion object {

        class MyHolder(val binding:ListItemBinding): RecyclerView.ViewHolder(binding.root)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.binding.textView.text = "${position+1}"
        holder.binding.textView2.text = "${data[position]}"
    }

    override fun getItemCount(): Int {
       return data.size
    }
}