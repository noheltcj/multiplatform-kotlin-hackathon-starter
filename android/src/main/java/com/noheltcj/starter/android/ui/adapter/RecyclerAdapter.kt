package com.noheltcj.starter.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.noheltcj.starter.android.BR

class RecyclerAdapter<T>(
    private val viewModel: Any,
    private val layoutResolver: (T) -> Int
) : RecyclerView.Adapter<RecyclerAdapter<T>.BindingViewHolder>() {
    var data: List<T> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return BindingViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.binding.also {
//            it.setVariable(BR.entity, data[position])
            it.setVariable(BR.vm, viewModel)
        }.executePendingBindings()
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = layoutResolver(data[position])

    inner class BindingViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val binding: ViewDataBinding = DataBindingUtil.bind(root)!!
    }
}