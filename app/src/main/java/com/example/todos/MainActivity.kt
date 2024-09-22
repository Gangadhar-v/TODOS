package com.example.todos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var floatingAddBtn: FloatingActionButton
    lateinit var notesRecyclerView: RecyclerView
    lateinit var notesAdapter: NotesAdapter
    lateinit var indicator:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
         indicator = findViewById<TextView>(R.id.indicate)

        floatingAddBtn = findViewById(R.id.addNoteflbtn)
        notesRecyclerView = findViewById(R.id.noteRecyclerView)


        notesAdapter = NotesAdapter(this,Database.database)


        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesRecyclerView.adapter = notesAdapter


        updateViewVisibility()

        floatingAddBtn.setOnClickListener {
            showDialog()

        }




    }

    @SuppressLint("MissingInflatedId")
    private fun showDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dailog, null)


        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)


        val alertDialog = dialogBuilder.create()


        val addBtn = dialogView.findViewById<Button>(R.id.btn_add_note)
        val editText = dialogView.findViewById<EditText>(R.id.et_note)


        addBtn.setOnClickListener {
            val noteText = editText.text.toString()


            if (noteText.isEmpty()) {
                editText.error = "Note cannot be empty"
            } else {

                Database.database.add(0,Note(noteText, false))


                notesAdapter.notifyItemInserted(0)
                notesRecyclerView.scrollToPosition(0) // Scroll to the newly added note

                Toast.makeText(this, "Note added!", Toast.LENGTH_SHORT).show()

                updateViewVisibility()

                alertDialog.dismiss()
            }
        }


        alertDialog.show()
    }
     fun updateViewVisibility() {
        if (Database.database.size != 0) {
            notesRecyclerView.visibility = View.VISIBLE
            indicator.visibility = View.GONE
        } else {
            indicator.visibility = View.VISIBLE
            notesRecyclerView.visibility = View.INVISIBLE
        }
    }
}
