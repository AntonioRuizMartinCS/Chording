package com.example.dp.adapters

import android.content.Context
import android.view.*
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dp.R
import com.example.dp.activities.MainActivity
import com.example.dp.activities.SetActivity
import com.example.dp.models.TabsViewModel

class TabsRVAdapter(
    private val mList: List<TabsViewModel>,
    private val listener: OnItemClickListener,
    private val menuItemClickListener: OnMenuItemClickListener
                    ) : RecyclerView.Adapter<TabsRVAdapter.ViewHolder>() {



    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tabs_card_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tabsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.songName.text = tabsViewModel.songName
        holder.artistName.text = tabsViewModel.artistName
        holder.optionsButton.setImageResource(tabsViewModel.optionsButton)
        holder.optionsButton.setOnClickListener { createPopUpMenu(holder.optionsButton, position, holder.itemView.context)}



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
   inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), OnClickListener {

        val songName: TextView = itemView.findViewById(R.id.cardSongName)
        var optionsButton: ImageView = itemView.findViewById(R.id.optionsButtonImageView)
        val artistName: TextView = itemView.findViewById(R.id.cardArtistName)

        init {
            itemView.setOnClickListener(this)
        }



        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }



    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface OnMenuItemClickListener{
        fun onDeleteClicked(position: Int)
        fun onEditClicked(position: Int)
    }

    private fun createPopUpMenu(view:View, position: Int, context:Context){

        val popupMenu = PopupMenu(view.context, view)

        when(context){

            is MainActivity ->  popupMenu.menuInflater.inflate(R.menu.tab_context_menu, popupMenu.menu)
            is SetActivity -> popupMenu.menuInflater.inflate(R.menu.song_in_set_context_menu, popupMenu.menu)
        }

        popupMenu.setOnMenuItemClickListener { item ->

            when(item.itemId){

                R.id.cmDelete -> menuItemClickListener.onDeleteClicked(position)

                R.id.removeFromSet -> menuItemClickListener.onDeleteClicked(position)

                R.id.cmEdit -> menuItemClickListener.onEditClicked(position)

            }
            true
        }
        popupMenu.show()
    }
}




