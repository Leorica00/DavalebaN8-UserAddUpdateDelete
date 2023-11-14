package com.example.davaleban8

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.davaleban8.databinding.ItemBinding

class UserRecyclerAdapter(private val users: MutableList<User>) :
    RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind()
        holder.updateUser()
        holder.deleteUser()
    }

    inner class UserViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val user = users[adapterPosition]
            with(binding) {
                inputNameTv.text = user.name
                inputSurnameTv.text = user.surname
                inputEmailTv.text = user.email
            }
        }

        fun updateUser() {
            binding.updateBtn.setOnClickListener {
                Intent(binding.root.context, UserActivity::class.java).also {
                    it.putExtra("user", users[adapterPosition])
                    it.putExtra("ButtonValue", "Update")
                    binding.root.context.startActivity(it)
                }
            }
        }

        fun deleteUser() {
            binding.deleteBtn.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    users.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }
    }
}