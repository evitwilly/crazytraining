package ru.freeit.crazytraining.core.theming.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CoreAdapter<T>(
    private val items: List<T>,
    private val viewHolder: (Context) -> CoreViewHolder<T>
) : RecyclerView.Adapter<CoreViewHolder<T>>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CoreViewHolder<T>, position: Int) { holder.bind(items[position]) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = viewHolder.invoke(parent.context)

}