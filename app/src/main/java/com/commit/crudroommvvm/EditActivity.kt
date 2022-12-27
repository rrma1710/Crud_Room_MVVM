package com.commit.crudroommvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.commit.crudroommvvm.databinding.ActivityEditBinding
import com.commit.crudroommvvm.room.Note
import com.commit.crudroommvvm.room.NoteDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    val db by lazy { NoteDB(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener() {
        binding.buttonSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addNote(
                    Note(0, binding.editTitle.text.toString(), binding.editNote.text.toString())
                )
                finish()
            }
        }
    }
}