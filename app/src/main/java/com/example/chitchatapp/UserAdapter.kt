package com.example.chitchatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth


class UserAdapter(val context: Context,val userList:ArrayList<User>): RecyclerView.Adapter<UserAdapter.userViewHolder>() {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
     val view:View=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
      return userViewHolder(view)
   }

   override fun getItemCount(): Int {
      return userList.size
   }

   override fun onBindViewHolder(holder: userViewHolder, position: Int) {
      val currentUser=userList[position]
      holder.name.text=currentUser.name
       holder.itemView.setOnClickListener{
           val intent= Intent(context,chatActivity::class.java)
           intent.putExtra("name",currentUser.name)
           intent.putExtra("uid",currentUser.uid)
           context.startActivity(intent)
       }
   }

   class userViewHolder(itemView: View) : ViewHolder(itemView){
       val name= itemView.findViewById<TextView>(R.id.namemainscreen)
   }

}