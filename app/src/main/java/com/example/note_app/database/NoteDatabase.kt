package com.example.note_app.database

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.note_app.models.Notes
import com.example.note_app.utils.DATABASE_NAME
import java.security.AccessControlContext


// ======================= for the database we are using room database ============================
// ======================= DB name is defined in the utils.constants file =========================

@Database(entities = arrayOf(Notes::class), version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase(){

    abstract fun getNoteDao() : Note_Dao

    companion object{

        @Volatile
        private var INSTANCE : NoteDatabase? = null

        fun getDataBase(context: Context): NoteDatabase{

            return INSTANCE?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}