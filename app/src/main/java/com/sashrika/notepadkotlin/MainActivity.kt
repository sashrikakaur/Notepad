package com.sashrika.notepadkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_done.*
import kotlinx.android.synthetic.main.item_row.*

//keep text size a multiple of 8
//Keyline pushing app by google->material design


class MainActivity : AppCompatActivity() {
    private val noteDb by lazy {
          NoteDataBase(this,"db",null,1)
    }

    private val notesnotdone by lazy {
        noteDb.getNotesNotdone()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val noteAdapter = NotesAdapter(notesnotdone)

        rvNotDone.layoutManager = LinearLayoutManager(this)

        rvNotDone.adapter = noteAdapter
//
        changenotdone.setOnClickListener {
            val intent = Intent(this, DoneActivity::class.java)
            startActivity(intent)
        }


        buttonNotDone.setOnClickListener {
            val note = Note(ettext.text.toString(),"Not Done")

            //Save this note to the db
            val id = noteDb.insert(note)

            if (id != -1L) {

                val insertedNote: Note? = noteDb.getNotebyId(id)

                insertedNote?.let { fetchedNote ->
                    notesnotdone.add(fetchedNote)
                    noteAdapter.notifyItemInserted(notesnotdone.size - 1)
                }

            } else
                Toast.makeText(this, "There was an error saving this to the db", Toast.LENGTH_SHORT).show()

        }



    }

}




/////
//*** button press update app 10save in db fetch from db and then add and dislpay
///***** 2)if condition to check if inserted then add
////*********** val id = noteDb.insertNote(...as shown in app

//arrayof ->toSTring type inferencing