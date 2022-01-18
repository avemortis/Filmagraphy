package com.example.filmagraphy.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.filmagraphy.data.model.Film


class FilmDiffUtils(val oldList : List<Film>, val newList : List <Film>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}