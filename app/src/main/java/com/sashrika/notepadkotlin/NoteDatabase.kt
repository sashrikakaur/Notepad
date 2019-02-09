package com.sashrika.notepadkotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//autoincrement id
class NoteDataBase(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, name, factory, version) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE notes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                note TEXT NOT NULL,
                status TEXT NOT NULL
            );
        """.trimIndent())

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("""DROP TABLE notes;""")
        onCreate(db)
    }


    fun getNotesNotdone(): ArrayList<Note> {

        val notes = arrayListOf<Note>()
        //Do something that populates the students array

        val noteCursor = readableDatabase.query(
                "notes",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        )

        while (noteCursor.moveToNext()) {
            val id = noteCursor.getLong(0)
            val note = noteCursor.getString(1)
            val status = noteCursor.getString(2)
            if (status == "Not Done") {

                val currNote =
                        Note(
                                id = id,
                                note = note,
                                status = status
                        )

                notes.add(currNote)
            }
        }

        noteCursor.close()

        return notes
    }

    fun getNotesdone() : ArrayList<Note>{
        val notes = arrayListOf<Note>()

        val noteCursor = readableDatabase.query(
                "notes",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        )

        while (noteCursor.moveToNext()) {
            val id = noteCursor.getLong(0)
            val note = noteCursor.getString(1)
            val status = noteCursor.getString(2)
            if (status == "Done") {

                val currNote =
                        Note(
                                id = id,
                                note = note,
                                status = status
                        )

                notes.add(currNote)
            }
        }

        noteCursor.close()

        return notes

    }

    fun insert(notes: Note): Long {
        //insert the student object in the database
        val contentValues = ContentValues()

        with(contentValues) {

            put("note", notes.note)
            put("status", notes.status)
//            put("address", "Delhi")
        }

        return writableDatabase.insert("notes", null, contentValues)
    }

    fun update(id : Long){
        //insert the student object in the database
        writableDatabase.execSQL("""UPDATE notes SET status = done WHERE id= ${id}""")
    }

    fun getNotebyId(id: Long): Note? {

        val cursor = readableDatabase.query(
                "notes",
                arrayOf("id", "note", "status"),
                "id = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
        )

        if (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            val note = cursor.getString(1)
            val status = cursor.getString(2)

            cursor.close()

            return Note(
                    id = id,
                    note = note,
                    status = status
            )
        }

        return null
    }

    //arrayListof benefit you can add value sthrough constructor need not use add

}