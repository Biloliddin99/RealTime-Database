package com.example.realtime_database

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.realtime_database.adapters.MyRvAdapter
import com.example.realtime_database.databinding.ActivityMainBinding
import com.example.realtime_database.models.MyMessage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var myRvAdapter: MyRvAdapter

    //    private lateinit var list: ArrayList<MyMessage>
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("my_message  ")

        myRvAdapter = MyRvAdapter()
        binding.myRv.adapter = myRvAdapter
        binding.apply {
            edtMessage.addTextChangedListener {
                if (edtMessage.text.isNotEmpty()) {
                    btnSend.visibility = View.VISIBLE
                    btnSend.setOnClickListener {
                        val id = reference.push().key
                        val myMessage = MyMessage(id, edtMessage.text.toString())
                        reference.child(id!!).setValue(myMessage)
                        edtMessage.text.clear()
                    }
                } else {
                    btnSend.visibility = View.GONE
                }
            }

        }


        reference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                val list = ArrayList<MyMessage>()
                for (child in children) {
                    val value = child.getValue(MyMessage::class.java)
                    if (value != null) {
                        list.add(value)
                    }
                }
                myRvAdapter.list.clear()
                myRvAdapter.list.addAll(list)
                myRvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}