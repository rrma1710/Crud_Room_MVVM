package com.commit.crudroommvvm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.commit.crudroommvvm.databinding.ActivityMainBinding
import com.commit.crudroommvvm.room.Constant
import com.commit.crudroommvvm.room.Note
import com.commit.crudroommvvm.room.NoteDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val db by lazy { NoteDB(this) }

    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(arrayListOf(), object : NoteAdapter.OnAdapterListener {
            override fun onClick(note: Note) {
                intentEdit(note.id,Constant.TYPE_READ)
            }

            override fun onUpdate(note: Note) {
                intentEdit(note.id,Constant.TYPE_UPDATE)
            }
        })
        binding.listNote.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }
    }

    private fun setupListener() {
        binding.buttonCreate.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNotes()
            Log.d("MainActivity", "dbResponse : $notes")
            withContext(Dispatchers.Main) {
                noteAdapter.setData(notes)
            }
        }
    }

    fun intentEdit(noteId: Int, intentType: Int) {
        startActivity(
            Intent(
                applicationContext,
                EditActivity::class.java)
                .putExtra("intent_id", noteId)
                .putExtra("intent_type", intentType)
        )
    }
}