package com.example.note_app.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app.R
import com.example.note_app.models.Notes
import kotlin.random.Random

class NoteAdapter(private val context: Context, val listener: NoteItemClickListener): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val NotesList = ArrayList<Notes>()

    private val fullList = ArrayList<Notes>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return NotesList.size
    }

    fun updateList(newList: List<Notes>){

        fullList.clear()
        fullList.addAll(newList)

        fullList.clear()
        fullList.addAll(fullList)

        notifyDataSetChanged()
    }

    fun filterList(search: String){
        NotesList.clear()

        for( item in fullList){
            if(item.title?.lowercase()?.contains(search.lowercase())== true || item.note?.lowercase()?.contains(search.lowercase())== true){
                 NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current_note = NotesList[position]
        holder.title.text = current_note.title
        holder.title.isSelected = true

        holder.Note_tv.text = current_note.note
        holder.date.text = current_note.date
        holder.date.isSelected = true


        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(RandomColor(),null ))

        holder.notes_layout.setOnClickListener {
            listener.onItemClicked(NotesList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener {
            listener.onLongItemClicked(NotesList[holder.adapterPosition], holder.notes_layout)
            true
        }

    }

    fun RandomColor(): Int {

        val list = ArrayList<Int>()
        list.add(R.color.color_1)
        list.add(R.color.color_2)
        list.add(R.color.color_3)
        list.add(R.color.color_4)
        list.add(R.color.color_5)
        list.add(R.color.color_6)
        list.add(R.color.color_7)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)

        return list[randomIndex]
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val Note_tv = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)


    }

    interface NoteItemClickListener{
        fun onItemClicked(notes: Notes)
        fun onLongItemClicked(notes: Notes, cardView: CardView )
    }
}