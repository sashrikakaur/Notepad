package com.sashrika.notepadkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_done.*

class DoneActivity : AppCompatActivity() {
    private val noteDb by lazy {
        NoteDataBase(this,"db",null,1)
    }
    private val notesdone by lazy {
        noteDb.getNotesdone()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_done)
        val noteAdapter = NotesAdapter(notesdone)
        changedone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        rvdone.layoutManager = LinearLayoutManager(this)

        rvdone.adapter = noteAdapter

        buttonDone.setOnClickListener {
            val note = Note(ettext2.text.toString(),"Done")

            //Save this note to the db
            val id = noteDb.insert(note)

            if (id != -1L) {

                val insertedNote: Note? = noteDb.getNotebyId(id)

                insertedNote?.let { fetchedNote ->
                    notesdone.add(fetchedNote)
                    noteAdapter.notifyItemInserted(notesdone.size - 1)
                }

            } else
                Toast.makeText(this, "There was an error saving this to the db", Toast.LENGTH_SHORT).show()

        }


    }


}