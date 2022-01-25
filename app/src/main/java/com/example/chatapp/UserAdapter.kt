package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context: Context, private val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        //create a layout inflater with the parent layout
        // and inflate(put) the user_layout as the inner
        // default layout to display the item
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.user_layout, parent, false)
        //return view holder
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        //here we bind the data with the view and view holder
        val currentUser = userList[position]

        holder.textName.text = currentUser.name
        //when the user click on the who he/she wants to chat with
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)
            //send data from this activity to ChatActivity
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        //return the number of user in list
        return  userList.size
    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.txt_name)
    }
}
