package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

//do not pass any view holder because there are multiple but pass the default
class MessageAdapter(var context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //this should help return the view
    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // inflate layout according to the return type of getItemViewType
        return if (viewType == 1){
            //inflate receive
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.receive, parent, false)
            //return view holder
            ReceiveViewHolder(view)
        } else{
            //inflate sent
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.send, parent, false)
            //return view holder
            SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //check which view holder to use
        if(holder.javaClass == SentViewHolder::class.java){
            //get message from message list
            val currentMessage = messageList[position]
            //action for sent view holder
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        }
        else{
            //get message from message list
            val currentMessage = messageList[position]
            //action for receive view holder
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

        // this help to return a particular view
    override fun getItemViewType(position: Int): Int {
        //get current message
        val currentMessage = messageList[position]
        //check if user is sending message
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    //create two view holder
    //one for sent message the second for receiving message
    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sentMessage: TextView = itemView.findViewById(R.id.text_send_message)
    }

    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveMessage: TextView = itemView.findViewById(R.id.text_receive_message)

    }
}
