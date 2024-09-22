package com.example.todos

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(var context: Context, var noteDatabase:ArrayList<Note>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    inner class NotesViewHolder(item:View): RecyclerView.ViewHolder(item){

        val checkBox = item.findViewById<CheckBox>(R.id.noteCheckBox);
        val textView = item.findViewById<TextView>(R.id.noteTextView);
        val deteteBtn = item.findViewById<ImageButton>(R.id.deleteBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.note_item,parent,false);

        return NotesViewHolder(view);
    }

    override fun getItemCount(): Int {
        return noteDatabase.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

       val note =  noteDatabase[position]
        holder.textView.text = note.data.toString()

        holder.checkBox.isChecked = note.isChecked

        if(note.isChecked){
            holder.textView.paintFlags = holder.textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            holder.textView.paintFlags = holder.textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.checkBox.setOnCheckedChangeListener { compoundButton, b ->
            note.isChecked = b;
            updateTextStrikethrough(holder.textView,b)
            holder.itemView.post {
                notifyItemChanged(position)
            }
        }

        holder.deteteBtn.setOnClickListener {
            noteDatabase.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,noteDatabase.size)
            Toast.makeText(context,"Note Deleted!",Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateTextStrikethrough(textView: TextView, isChecked: Boolean) {
        if (isChecked) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }


}