package com.example.todoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

data class SpinnerItem (
    val res : Int,
    val text : String
)

class SpinnerAdapter(private val items : List<SpinnerItem>, private val ctx : Context) : BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.custom_spinner, p2, false)

        val image : ImageView = view.findViewById(R.id.spinner_item_image)
        val text : TextView = view.findViewById(R.id.spinner_item_text)

        val item = getItem(p0) as SpinnerItem

        image.setImageResource(item.res)
        text.text = item.text
        return view
    }
}