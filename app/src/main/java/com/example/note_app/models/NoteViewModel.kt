package com.example.note_app.models

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.note_app.database.NoteDatabase
import com.example.note_app.database.NotesRepository
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application){

    private val repository: NotesRepository

    val allnotes : LiveData<List<Notes>>

    init {
        val dao = NoteDatabase.getDataBase(application).getNoteDao()
        repository = NotesRepository(dao)
        allnotes = repository.allNotes
    }

    fun deleteNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO){
        repository.Delete(notes)
    }

    fun insertNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO){
        repository.Insert(notes)
    }
    fun updateNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO){
        repository.Update(notes)
    }
}