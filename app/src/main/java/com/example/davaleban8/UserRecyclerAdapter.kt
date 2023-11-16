package com.example.davaleban8

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.davaleban8.databinding.ItemBinding

class UserRecyclerAdapter :
    ListAdapter<User, UserRecyclerAdapter.UserViewHolder>(
        object : DiffUtil.ItemCallback<User>(){

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.email == newItem.email
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    ) {
    var onUpdateClick: ((User) -> Unit)? = null
    var onDeleteClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind()
        holder.deleteUser()
    }

    inner class UserViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val user = currentList[adapterPosition]
            with(binding) {
                inputNameTv.text = user.name
                inputSurnameTv.text = user.surname
                inputEmailTv.text = user.email
            }
            binding.updateBtn.setOnClickListener {
                onUpdateClick?.invoke(user)
            }
        }

        fun deleteUser() {
            binding.deleteBtn.setOnClickListener {
                onDeleteClick?.invoke(currentList[adapterPosition])
            }
        }
    }
}