package com.example.chitchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchatapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatActivity : AppCompatActivity() {
    lateinit var chatRecyclerView: RecyclerView
    lateinit var messageBox:EditText
    lateinit var sentButton:ImageView
    lateinit var messageAdapter: MessageAdapter
    lateinit var messageList: ArrayList<Message>
    lateinit var dbRef:DatabaseReference
    var receiverRoom:String?=null
    var senderRoom:String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val receiveruid=intent.getStringExtra("uid")
        val senderuid=FirebaseAuth.getInstance().currentUser?.uid

        dbRef=FirebaseDatabase.getInstance().getReference()

        senderRoom=receiveruid + senderuid
        receiverRoom=senderuid + receiveruid

        supportActionBar?.title=name
        chatRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.msgBox)
        sentButton=findViewById(R.id.send)
        messageList=ArrayList()
        messageAdapter=MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter


        //adding data to recycler view

        dbRef.child("chats").child(senderRoom!!).child("messages ")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message=postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        //adding the message to database
        sentButton.setOnClickListener{
           val message=messageBox.text.toString()
            val messageObject=Message(message,senderuid)
            dbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    dbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }
    }
}