package com.example.davaleban8

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.davaleban8.databinding.ActivityUsersBinding

class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    private lateinit var addButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var users: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addButton = binding.addBtn
        recyclerView = binding.recycler
        users = UserListSingleton.users
        setUp()
    }

    override fun onResume() {
        super.onResume()
        val data: Int = intent.getIntExtra("UserIndex", Int.MAX_VALUE)
        if (data != Int.MAX_VALUE){
            recyclerView.adapter?.notifyItemChanged(data)
        }
    }

    private fun setUp() {
        setUpUsersRecyclerView()
        setUpOnAddClick()
    }

    private fun setUpUsersRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserRecyclerAdapter(users = users)
    }


    private fun setUpOnAddClick() {
        addButton.setOnClickListener {
            Intent(this, UserActivity::class.java).also {
                it.putExtra("ButtonValue", "Add")
                launchSomeActivity.launch(it)
            }
        }
    }

    private val launchSomeActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK)
            recyclerView.adapter?.notifyItemInserted(users.size - 1)
    }
}