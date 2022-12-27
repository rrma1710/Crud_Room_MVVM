package com.commit.crudroommvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.commit.crudroommvvm.room.Note
import kotlinx.android.synthetic.main.adapter_note.view.*

class NoteAdapter(private val note: ArrayList<Note>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_note, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = note[position]
        holder.view.text_title.text = note.title
        holder.view.text_title.setOnClickListener {
            listener.onClick(note)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(note)
        }
        holder.view.icon_delete.setOnClickListener {


        }
    }

    override fun getItemCount(): Int = note.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Note>) {
        note.clear()
        note.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(note: Note)
        fun onUpdate(note: Note)
    }
}
