package com.example.davaleban8

import android.content.Intent
import android.os.Bundle
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
        if (data != Int.MAX_VALUE) {
            recyclerView.adapter?.notifyItemChanged(data)
        }
    }

    private fun setUp() {
        setUpUsersRecyclerView()
        setUpOnAddClick()
    }

    private fun setUpUsersRecyclerView() {
        val userRecyclerAdapter = UserRecyclerAdapter(users = users)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userRecyclerAdapter
        userRecyclerAdapter.onItemClick = {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("user", it)
            intent.putExtra("ButtonValue", "Update")
            launchUpdateActivity.launch(intent)
        }
    }

    private fun setUpOnAddClick() {
        addButton.setOnClickListener {
            Intent(this, UserActivity::class.java).also {
                it.putExtra("ButtonValue", "Add")
                launchAddActivity.launch(it)
            }
        }
    }

    private val launchUpdateActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK){
            val data = it.data
            val index = data?.getIntExtra("UserIndex", -1)
            if (index != -1){
                recyclerView.adapter?.notifyItemChanged(index!!)
            }

        }

    }

    private val launchAddActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK)
            recyclerView.adapter?.notifyItemInserted(users.size - 1)
    }
}