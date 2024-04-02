package com.example.dp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dp.R
import com.example.dp.models.SetsViewModel


class SetsRVAdapter(
    private val mList: List<SetsViewModel>,
    private val listener: OnItemClickListener,
    private val menuItemClickListener: OnMenuItemClickListener
) : RecyclerView.Adapter<SetsRVAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sets_card_layout, parent, false)

        val sharedPreferences = view.context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("darkMode", false)){
            view.background = ContextCompat.getDrawable(view.context, R.drawable.card_view_night_mode_background)
        }else{
            view.background = ContextCompat.getDrawable(view.context, R.drawable.card_view_day_mode_background)
        }
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val setsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.setName.text = setsViewModel.setName
        holder.optionsButton.setImageResource(setsViewModel.optionsButton)
        holder.optionsButton.setOnClickListener { createPopUpMenu(holder.optionsButton, position)}



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView),
        View.OnClickListener {

        val setName: TextView = itemView.findViewById(R.id.cardSetName)
        var optionsButton: ImageView = itemView.findViewById(R.id.optionsButtonImageView)

        init {
            itemView.setOnClickListener(this)
        }



        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemSetClick(position)
            }
        }



    }
    interface OnItemClickListener{
        fun onItemSetClick(position: Int)
    }

    interface OnMenuItemClickListener{
        fun onDeleteSetClicked(position: Int)
        fun onEditSetClicked(position: Int)

        fun onAddSongToSetClicked(position: Int)
    }

    private fun createPopUpMenu(view: View, position: Int){

        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(R.menu.set_context_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId){

                R.id.cmDelete -> menuItemClickListener.onDeleteSetClicked(position)

                R.id.cmEdit -> menuItemClickListener.onEditSetClicked(position)

                R.id.cmAddSong -> menuItemClickListener.onAddSongToSetClicked(position)

            }
            true
        }
        popupMenu.show()
    }
}