package com.example.note_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.note_app.adaptor.NoteAdapter
import com.example.note_app.database.NotesRepository
import com.example.note_app.database.NoteDatabase
import com.example.note_app.databinding.ActivityMainBinding
import com.example.note_app.models.NoteViewModel
import com.example.note_app.models.Notes


class MainActivity : ComponentActivity(), NoteAdapter.NoteItemClickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel:NoteViewModel
    lateinit var adapter: NoteAdapter
    lateinit var selectedNote: Notes

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if (result.resultCode == Activity.RESULT_OK){
            val note = result.data?.getSerializableExtra("note") as Notes
            if (note!= null){
                viewModel.updateNote(note)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root )


        //initate the ui
        initUi()

        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allnotes.observe(this){
            list -> list?.let {
                adapter.updateList(list)
        }
        }

        database = NoteDatabase.getDataBase(this)
    }

    private fun initUi(){
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)  //this is for showing the note in layout
        adapter = NoteAdapter(this,this)
        binding.recyclerView.adapter = adapter


        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            if (result.resultCode == Activity.RESULT_OK ){
                val note = result.data?.getSerializableExtra("note") as Notes
                if ( note != null){
                    viewModel.insertNote(note)
                }
            }
        }

        // this activity will be created as a result launch activity

        binding.addNote.setOnClickListener{
            val intent = Intent(this,AddNote::class.java)
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!= null){
                    adapter.filterList(newText)
                }
                return true
            }

        })

    }

    override fun onItemClicked(note: Notes) {

        val intent = Intent(this@MainActivity,AddNote::class.java )
        intent.putExtra("current_note", note)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(note: Notes, cardView: CardView) {
         selectedNote = note
        popupDisplay(cardView)
    }

    private fun popupDisplay(cardView: CardView) {
        val popup = PopupMenu(this,cardView )
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.delete_note){
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }
}

