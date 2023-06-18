package com.example.note_app.database

import android.graphics.Insets
import android.icu.text.CaseMap.Title
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.note_app.models.Notes

//============================== Maintain Interface =======================

// =========================== using Corotines ===========================

@Dao
interface Note_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(note: Notes)

    @Delete
    suspend fun Delete(note: Notes)

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Notes>>

    @Query("UPDATE notes_table set title = :title, note = :note WHERE id= :id")
    suspend fun Update(id:Int?,title: String?,note: String?)

}