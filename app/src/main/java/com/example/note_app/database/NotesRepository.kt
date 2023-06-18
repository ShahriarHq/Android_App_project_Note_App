package com.example.note_app.database

import android.provider.ContactsContract.Intents.Insert
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Update
import com.example.note_app.models.Notes

class NotesRepository(private val noteDao: Note_Dao )  {

    val allNotes: LiveData<List<Notes>> = noteDao.getAllNotes()


    suspend fun Insert(note: Notes){

        noteDao.Insert(note)
    }

    suspend fun Delete(note: Notes){

        noteDao.Delete(note)
    }


    suspend fun Update(note: Notes){

        noteDao.Update(note.id,note.title,note.note)
    }

}