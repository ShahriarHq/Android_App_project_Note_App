package com.example.note_app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//======================== concept of ROOM DB ===============================

//=========================== DB Creation =======================
@Entity(tableName = "notes_table")
data class Notes(
    @PrimaryKey(autoGenerate = true)val id : Int?,
    @ColumnInfo(name = "title")val title: String?,
    @ColumnInfo(name = "note")val note: String?,
    @ColumnInfo(name = "date ")val date: String?


) : Serializable
