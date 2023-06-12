package ru.freeit.crazytraining.core.theming.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class CoreViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)
}